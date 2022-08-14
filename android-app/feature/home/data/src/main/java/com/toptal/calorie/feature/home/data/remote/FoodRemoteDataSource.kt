package com.toptal.calorie.feature.home.data.remote

import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface FoodRemoteDataSource {
    fun getFoodList(userId: String? = null, startDate: Date? = null, endDate: Date? = null): Flow<List<FoodDomainModel>>
    fun saveFood(name: String, calorie: Int, userId: String? = null): Flow<Unit>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
}