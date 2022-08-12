package com.toptal.calorie.feature.home.domain.repository

import androidx.paging.PagingData
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun fetchFoodList(): Flow<PagingData<FoodDomainModel>>
    fun saveFoodList(userId: String?): Flow<Unit>
    fun saveFood(name: String, calorie: Int): Flow<Unit>
    fun deleteFood(foodId: String): Flow<Unit>
    fun updateFood(foodId: String, name: String, calorie: Int): Flow<Unit>
}