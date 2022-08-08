package com.toptal.calorie.feature.home.domain.usecase

import androidx.paging.PagingData
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodUseCase {
    fun fetchFoodItems(): Flow<PagingData<FoodDomainModel>>
    fun saveFood(foodDomainModel: FoodDomainModel): Flow<Unit>
}