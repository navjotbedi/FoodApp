package com.toptal.calorie.feature.home.domain.repository

import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoodList(): Flow<List<FoodDomainModel>>
    fun saveFood(name: String, calorie: Int): Flow<Unit>
}