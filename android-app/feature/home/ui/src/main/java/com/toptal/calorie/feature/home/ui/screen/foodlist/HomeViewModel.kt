package com.toptal.calorie.feature.home.ui.screen.foodlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.home.domain.usecase.FoodUseCase
import com.toptal.calorie.feature.home.ui.entity.FoodUIModel
import com.toptal.calorie.feature.home.ui.entity.HeaderUIModel
import com.toptal.calorie.feature.home.ui.entity.mapper.FoodUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    private val mapper: FoodUIMapper
) : ViewModel() {

    init {
        saveFoodList()
    }

    var userId: String? = null
        private set

    fun storeUserId(userId: String?) {
        this.userId = userId
    }

    fun isAdmin() = userId != null

    fun fetchFoodList() = foodUseCase.fetchFoodList()
        .map { pagingData -> pagingData.map { withContext(Dispatchers.IO) { mapper.map(it) } } }
        .map {
            it.insertSeparators { oldFoodUIModel: FoodUIModel?, newFoodUIModel: FoodUIModel? ->
                if (newFoodUIModel == null) return@insertSeparators null

                if (newFoodUIModel.intakeDate != null) {
                    if (oldFoodUIModel == null) return@insertSeparators HeaderUIModel(newFoodUIModel.intakeDate, 100)
                    if (oldFoodUIModel.intakeDate != null)
                        if (oldFoodUIModel.intakeDate != newFoodUIModel.intakeDate) return@insertSeparators HeaderUIModel(newFoodUIModel.intakeDate, 100)
                }
                return@insertSeparators null
            }
        }
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    fun saveFoodList() {
        viewModelScope.launch {
            (foodUseCase.saveFoodList(userId)
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

}