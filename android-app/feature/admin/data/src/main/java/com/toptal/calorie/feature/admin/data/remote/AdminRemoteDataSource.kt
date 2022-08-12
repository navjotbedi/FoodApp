package com.toptal.calorie.feature.admin.data.remote

import com.toptal.calorie.feature.admin.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface AdminRemoteDataSource {
    fun fetchUsers(): Flow<List<UserDomainModel>>
    fun getFoodList(userId: String): Flow<List<FoodDomainModel>>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
    fun addFood(name: String, calorie: Int): Flow<Unit>
}