package com.toptal.calorie.feature.home.data.remote

import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodRemoteDataSource {
    fun getFoodList(userId: String): Flow<List<FoodDomainModel>>
    fun saveFood(name: String, calorie: Int): Flow<FoodDomainModel>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
}