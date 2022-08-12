package com.toptal.calorie.feature.home.domain.repository

import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoodList(userId: String?): Flow<List<FoodDomainModel>>
    fun saveFood(name: String, calorie: Int): Flow<Unit>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
}