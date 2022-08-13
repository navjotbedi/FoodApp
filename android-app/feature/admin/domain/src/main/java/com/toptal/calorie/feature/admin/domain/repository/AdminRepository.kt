package com.toptal.calorie.feature.admin.domain.repository

import com.toptal.calorie.feature.admin.domain.entity.FoodReportDomainModel
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    fun fetchUsers(): Flow<List<UserDomainModel>>
    fun fetchFoodReport(): Flow<FoodReportDomainModel>
}