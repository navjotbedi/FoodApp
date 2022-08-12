package com.toptal.calorie.feature.admin.domain.usecase

import com.toptal.calorie.feature.admin.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface AdminUseCase {
    fun fetchUsers(): Flow<List<UserDomainModel>>
    fun fetchFoodItems(userId: String): Flow<List<FoodDomainModel>>
    fun addFood(name: String, calorie: Int): Flow<Unit>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
}