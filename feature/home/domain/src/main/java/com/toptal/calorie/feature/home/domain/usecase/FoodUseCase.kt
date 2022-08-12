package com.toptal.calorie.feature.home.domain.usecase

import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodUseCase {
    fun fetchFoodItems(): Flow<List<FoodDomainModel>>
    fun saveFood(name: String, calorie: Int): Flow<Unit>
}