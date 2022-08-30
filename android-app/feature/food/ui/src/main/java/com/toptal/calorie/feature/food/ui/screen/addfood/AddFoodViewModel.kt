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

    private var foodId: String? = null
    private var userId: String? = null

    var foodName by mutableStateOf("")
    var calorie by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var isEnable by mutableStateOf(false)
    var foodButtonText by mutableStateOf(R.string.add_food_text)
    var isDeleteButtonVisible by mutableStateOf(false)

    init {
        userId = savedStateHandle.get<String>(Constants.USER_ID_INTENT)
        val foodUIModel = savedStateHandle.get<String>(Constants.FOOD_INTENT)?.let { Json.decodeFromString(FoodUIModel.serializer(), it) }
        foodUIModel?.let {
            foodId = it.id
            foodName = it.name
            calorie = it.calorie
            isDeleteButtonVisible = true
            isEnable = true
        }
        foodButtonText = if (isAdmin()) R.string.update_food_text else R.string.add_food_text
        isDeleteButtonVisible = isAdmin()
    }

    private val _addFood = MutableLiveData<ResultState<Unit>>()
    val addFood: LiveData<ResultState<Unit>> = _addFood

    private fun isAdmin() = foodId != null

    fun saveFood() {
        viewModelScope.launch {
            ((if (isAdmin())
                foodUseCase.updateFood(foodId!!, foodName, calorie.toInt())
            else
                foodUseCase.saveFood(foodName, calorie.toInt(), userId))
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
            (foodUseCase.deleteFood(foodId!!)
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