package com.toptal.calorie.feature.home.data.local

import androidx.paging.PagingData
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.Flow

interface FoodLocalDataSource {
    fun getUserToken(): Flow<String>
    fun getFoodList(): Flow<PagingData<FoodDomainModel>>
    fun saveFoodList(foodList: List<FoodDomainModel>): Flow<Unit>
}