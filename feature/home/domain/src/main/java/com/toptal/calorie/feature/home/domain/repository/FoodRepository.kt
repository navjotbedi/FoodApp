package com.toptal.calorie.feature.home.domain.repository

import androidx.paging.PagingData
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoodList(): Flow<PagingData<FoodDomainModel>>
}