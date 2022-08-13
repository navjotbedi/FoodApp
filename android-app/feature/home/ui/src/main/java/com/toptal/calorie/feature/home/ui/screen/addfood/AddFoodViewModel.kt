package com.toptal.calorie.feature.home.ui.screen.addfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.home.domain.usecase.FoodUseCase
import com.toptal.calorie.feature.home.ui.entity.mapper.FoodUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    private val mapper: FoodUIMapper
) : ViewModel() {

    private val _addFood = MutableLiveData<ResultState<Unit>>()
    val addFood: LiveData<ResultState<Unit>> = _addFood

    var foodId: String? = null
        private set

    fun storeFoodId(foodId: String?) {
        this.foodId = foodId
    }

    fun isAdmin() = foodId != null

    fun saveFood(name: String, calorie: String, userId: String?) {
        viewModelScope.launch {
            ((if (isAdmin())
                foodUseCase.updateFood(foodId!!, name, calorie.toInt())
            else
                foodUseCase.saveFood(name, calorie.toInt(), userId))
                .map {
                    ResultState.Success(Unit)
                } as Flow<ResultState<Unit>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _addFood.value = it }
        }
    }

    fun deleteFood() {
        viewModelScope.launch {
            (foodUseCase.deleteFood(foodId!!)
                .map {
                    ResultState.Success(Unit)
                } as Flow<ResultState<Unit>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { emit(ResultState.Progress(true)) }
                .onCompletion { emit(ResultState.Progress(false)) }
                .conflate()
                .collect { _addFood.value = it }
        }
    }

}