package com.toptal.calorie.feature.food.data.local

import com.toptal.calorie.feature.food.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface FoodLocalDataSource {
    fun getUserToken(): Flow<String>
    fun getFoodList(startDate: Date? = null, endDate: Date? = null): Flow<List<FoodDomainModel>>
    fun saveFoodList(foodList: List<FoodDomainModel>): Flow<Unit>
    fun clearTable(): Flow<Unit>
}