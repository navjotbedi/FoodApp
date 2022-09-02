package com.toptal.calorie.feature.food.ui.screen.foodlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.toptal.calorie.core.utils.Constants
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.food.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.food.domain.usecase.FoodUseCase
import com.toptal.calorie.feature.food.ui.entity.HeaderUIModel
import com.toptal.calorie.feature.food.ui.entity.mapper.FoodUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    private val mapper: FoodUIMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var initialFetch = true
    var userId: String? = null
        private set

    init {
        userId = savedStateHandle.get<String>(Constants.USER_ID_INTENT)
    }

    fun isAdmin() = userId != null

    private val _foodList = MutableLiveData<List<Any>>()
    val foodList: LiveData<List<Any>> = _foodList
    var isLoading by mutableStateOf(false)
    var isDateFilterApplied by mutableStateOf(false)

    @OptIn(FlowPreview::class)
    fun fetchFoodList(startDate: Date? = null, endDate: Date? = null) {
        viewModelScope.launch {
            isDateFilterApplied = startDate != null && endDate != null
            val clearLocalCache = if (initialFetch) {
                initialFetch = false
                foodUseCase.clearLocalCache()
            } else {
                flow { emit(Unit) }
            }
            clearLocalCache.flatMapConcat { foodUseCase.fetchFoodList(startDate, endDate) }
                .map { computeUIModelList(it) }
                .flowOn(Dispatchers.IO)
                .collect {
                    _foodList.value = it
                }
        }
    }

    @OptIn(FlowPreview::class)
    fun saveFoodList() {
        viewModelScope.launch {
            (foodUseCase.clearLocalCache().flatMapConcat { foodUseCase.saveFoodList(userId) }
                .map { ResultState.Success(it) } as Flow<ResultState<Unit>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { isLoading = true }
                .onCompletion { isLoading = false }
                .conflate()
                .collect { }
        }
    }

    private fun computeUIModelList(foodDomainModelList: List<FoodDomainModel>): List<Any> {
        val uiModel = mutableListOf<Any>()
        var oldFoodUIDate: String? = null
        var dailyCalorie = 0
        var lastHeaderSection = 0
        foodDomainModelList.forEach { foodDomainModel ->
            val foodUIModel = mapper.map(foodDomainModel)
            if (oldFoodUIDate != null) {
                if (oldFoodUIDate != foodUIModel.intakeDate) {
                    (uiModel[lastHeaderSection] as HeaderUIModel).averageCalorie = String.format("%.2f", dailyCalorie.toFloat() / (uiModel.size - lastHeaderSection - 1))
                    uiModel.add(HeaderUIModel(foodUIModel.intakeDate!!, ""))
                    lastHeaderSection = uiModel.size - 1
                    dailyCalorie = 0
                }
            } else {
                uiModel.add(HeaderUIModel(foodUIModel.intakeDate!!, ""))
            }

            dailyCalorie += foodDomainModel.calorie
            uiModel.add(foodUIModel)
            oldFoodUIDate = foodUIModel.intakeDate
        }

        if (uiModel.isNotEmpty()) (uiModel[lastHeaderSection] as HeaderUIModel).averageCalorie = String.format("%.2f", dailyCalorie.toFloat() / (uiModel.size - lastHeaderSection - 1))
        return uiModel
    }

}