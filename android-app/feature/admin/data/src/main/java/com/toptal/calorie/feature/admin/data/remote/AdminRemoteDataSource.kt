package com.toptal.calorie.feature.admin.data.remote

import com.toptal.calorie.feature.admin.domain.entity.FoodReportDomainModel
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface AdminRemoteDataSource {
    fun fetchUsers(): Flow<List<UserDomainModel>>
    fun fetchFoodReport(): Flow<FoodReportDomainModel>
}