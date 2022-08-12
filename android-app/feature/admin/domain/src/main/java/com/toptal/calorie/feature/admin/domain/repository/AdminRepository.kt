package com.toptal.calorie.feature.admin.domain.repository

import com.toptal.calorie.feature.admin.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    fun fetchUsers(): Flow<List<UserDomainModel>>
    fun getFoodList(userId: String): Flow<List<FoodDomainModel>>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
    fun addFood(name: String, calorie: Int): Flow<Unit>
}