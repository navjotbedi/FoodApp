package com.toptal.calorie.feature.home.ui.screen.foodlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.home.domain.usecase.FoodUseCase
import com.toptal.calorie.feature.home.ui.entity.HeaderUIModel
import com.toptal.calorie.feature.home.ui.entity.mapper.FoodUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    private val mapper: FoodUIMapper
) : ViewModel() {
    var initialFetch = true
    var userId: String? = null
        private set

    fun storeUserId(userId: String?) {
        this.userId = userId
    }

    fun isAdmin() = userId != null

    @OptIn(FlowPreview::class)
    fun fetchFoodList(): Flow<List<Any>> {
        val clearLocalCache = if (initialFetch) {
            initialFetch = false
            foodUseCase.clearLocalCache()
        } else flow { emit(Unit) }
        return clearLocalCache.flatMapConcat { foodUseCase.fetchFoodList() }
            .map { computeUIModelList(it) }
            .flowOn(Dispatchers.IO)
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
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
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