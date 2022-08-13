package com.toptal.calorie.feature.admin.data

import com.toptal.calorie.feature.admin.data.remote.AdminRemoteDataSource
import com.toptal.calorie.feature.admin.domain.repository.AdminRepository
import javax.inject.Inject

internal class AdminRepositoryImpl @Inject constructor(
    private val remoteDataSource: AdminRemoteDataSource
) : AdminRepository {
    override fun fetchUsers() = remoteDataSource.fetchUsers()
    override fun fetchFoodReport() = remoteDataSource.fetchFoodReport()
    override fun fetchAvgCaloriePerUser() = remoteDataSource.fetchAvgCaloriePerUser()
}