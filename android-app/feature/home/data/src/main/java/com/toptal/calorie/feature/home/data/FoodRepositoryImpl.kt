package com.toptal.calorie.feature.home.data

import com.toptal.calorie.feature.home.data.local.FoodLocalDataSource
import com.toptal.calorie.feature.home.data.remote.FoodRemoteDataSource
import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import java.util.*
import javax.inject.Inject

internal class FoodRepositoryImpl @Inject constructor(
    private val remoteDataSource: FoodRemoteDataSource,
    private val localDataSource: FoodLocalDataSource
) : FoodRepository {
    override fun fetchFoodList(startDate: Date?, endDate: Date?) = localDataSource.getFoodList(startDate, endDate)
    override fun clearLocalCache() = localDataSource.clearTable()

    @OptIn(FlowPreview::class)
    override fun saveFoodList(userId: String?) = remoteDataSource.getFoodList(userId).flatMapConcat { localDataSource.saveFoodList(it) }
    override fun saveFood(name: String, calorie: Int, userId: String?) = remoteDataSource.saveFood(name, calorie, userId)
    override fun deleteFood(foodId: String) = remoteDataSource.deleteFood(foodId)
    override fun updateFood(foodId: String, name: String, calorie: Int) = remoteDataSource.updateFood(foodId, name, calorie)
}