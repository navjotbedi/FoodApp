package com.toptal.calorie.feature.admin.data

import com.toptal.calorie.feature.admin.data.remote.AdminRemoteDataSource
import com.toptal.calorie.feature.admin.domain.repository.AdminRepository
import javax.inject.Inject

internal class AdminRepositoryImpl @Inject constructor(
    private val remoteDataSource: AdminRemoteDataSource
) : AdminRepository {
    override fun fetchUsers() = remoteDataSource.fetchUsers()
    override fun getFoodList(userId: String) = remoteDataSource.getFoodList(userId)
    override fun deleteFood(foodId: String) = remoteDataSource.deleteFood(foodId)
    override fun updateFood(foodId: String, name: String, calorie: Int) = remoteDataSource.updateFood(foodId, name, calorie)
    override fun addFood(name: String, calorie: Int) = remoteDataSource.addFood(name, calorie)
}