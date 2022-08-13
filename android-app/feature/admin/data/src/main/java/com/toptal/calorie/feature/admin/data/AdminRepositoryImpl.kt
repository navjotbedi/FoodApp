package com.toptal.calorie.feature.admin.data

import com.toptal.calorie.feature.admin.data.remote.AdminRemoteDataSource
import com.toptal.calorie.feature.admin.domain.entity.FoodReportDomainModel
import com.toptal.calorie.feature.admin.domain.repository.AdminRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AdminRepositoryImpl @Inject constructor(
    private val remoteDataSource: AdminRemoteDataSource
) : AdminRepository {
    override fun fetchUsers() = remoteDataSource.fetchUsers()
    override fun fetchFoodReport() = remoteDataSource.fetchFoodReport()
}