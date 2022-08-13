package com.toptal.calorie.feature.home.domain.usecase

import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodUseCase {
    fun fetchFoodList(): Flow<List<FoodDomainModel>>
    fun clearLocalCache(): Flow<Unit>
    fun saveFoodList(userId: String?): Flow<Unit>
    fun saveFood(name: String, calorie: Int): Flow<Unit>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
}