package com.toptal.calorie.feature.home.ui.screen.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.home.domain.usecase.FoodUseCase
import com.toptal.calorie.feature.home.ui.entity.Food
import com.toptal.calorie.feature.home.ui.entity.mapper.FoodUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    private val mapper: FoodUIMapper
) : ViewModel() {

    private val _foodItems = MutableLiveData<ResultState<List<Food>>>()
    val foodItems: LiveData<ResultState<List<Food>>> = _foodItems

    fun fetchFoodList(userId: String?) {
        viewModelScope.launch {
            (foodUseCase.fetchFoodItems(userId)
                .map { it.map { foodDomainModel -> mapper.map(foodDomainModel) } }
                .map { ResultState.Success(it) } as Flow<ResultState<List<Food>>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _foodItems.value = it }
        }
    }

}