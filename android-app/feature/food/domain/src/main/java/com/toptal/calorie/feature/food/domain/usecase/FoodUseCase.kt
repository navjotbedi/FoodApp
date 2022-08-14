package com.toptal.calorie.feature.food.domain.usecase

import com.toptal.calorie.feature.food.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface FoodUseCase {
    fun fetchFoodList(startDate: Date? = null, endDate: Date? = null): Flow<List<FoodDomainModel>>
    fun clearLocalCache(): Flow<Unit>
    fun saveFoodList(userId: String?): Flow<Unit>
    fun saveFood(name: String, calorie: Int, userId: String? = null): Flow<Unit>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
}