package com.toptal.calorie.feature.food.ui.screen.addfood

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.toptal.calorie.core.utils.Constants
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.food.domain.usecase.FoodUseCase
import com.toptal.calorie.feature.food.ui.R
import com.toptal.calorie.feature.food.ui.entity.FoodUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var userId: String? = null
    private val _addFood by lazy { MutableLiveData<ResultState<Unit>>() }
    val addFood: LiveData<ResultState<Unit>> = _addFood
    private val _foodModel by lazy { MutableLiveData<FoodUIModel>() }
    val foodModel: LiveData<FoodUIModel> = _foodModel

    var isLoading by mutableStateOf(false)
    var isEnable by mutableStateOf(false)
    var foodButtonText by mutableStateOf(R.string.add_food_text)
    var isDeleteButtonVisible by mutableStateOf(false)

    init {
        userId = savedStateHandle.get<String>(Constants.USER_ID_INTENT)
        _foodModel.value = savedStateHandle.get<String>(Constants.FOOD_INTENT)?.let { Json.decodeFromString(FoodUIModel.serializer(), it) }
        if (foodModel.value != null) {
            isDeleteButtonVisible = true
            isEnable = true
            foodButtonText = R.string.update_food_text
        } else {
            foodButtonText = R.string.add_food_text
        }
    }

    fun saveFood(foodName: String, calorie: String) {
        viewModelScope.launch {
            ((foodModel.value?.id?.let {
                foodUseCase.updateFood(it, foodName, calorie.toInt())
            } ?: foodUseCase.saveFood(foodName, calorie.toInt(), userId))
                .map {
                    ResultState.Success(Unit)
                } as Flow<ResultState<Unit>>)
                .catch {
                    it.printStackTrace()
                    emit(ResultState.Error(it, "Something went wrong!"))
                }
                .flowOn(Dispatchers.IO)
                .onStart { isLoading = true }
                .onCompletion { isLoading = false }
                .conflate()
                .collect { _addFood.value = it }
        }
    }

    fun deleteFood() {
        viewModelScope.launch {
            foodModel.value?.id?.let { foodId ->
                (foodUseCase.deleteFood(foodId)
                    .map {
                        ResultState.Success(Unit)
                    } as Flow<ResultState<Unit>>)
                    .catch {
                        it.printStackTrace()
                        emit(ResultState.Error(it, "Something went wrong!"))
                    }
                    .flowOn(Dispatchers.IO)
                    .onStart { isLoading = true }
                    .onCompletion { isLoading = false }
                    .conflate()
                    .collect { _addFood.value = it }
            }
        }
    }
}