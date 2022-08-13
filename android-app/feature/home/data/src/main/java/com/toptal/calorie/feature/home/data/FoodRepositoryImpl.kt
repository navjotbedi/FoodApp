package com.toptal.calorie.feature.home.data

import com.toptal.calorie.feature.home.data.local.FoodLocalDataSource
import com.toptal.calorie.feature.home.data.remote.FoodRemoteDataSource
import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

internal class FoodRepositoryImpl @Inject constructor(
    private val remoteDataSource: FoodRemoteDataSource,
    private val localDataSource: FoodLocalDataSource
) : FoodRepository {
    override fun fetchFoodList() = localDataSource.getFoodList()
    override fun clearLocalCache() = localDataSource.clearTable()

    @OptIn(FlowPreview::class)
    override fun saveFoodList(userId: String?) =
        (userId?.let { remoteDataSource.getFoodList(it) } ?: remoteDataSource.getFoodList()).flatMapConcat { localDataSource.saveFoodList(it) }

    override fun saveFood(name: String, calorie: Int) = remoteDataSource.saveFood(name, calorie)
    override fun deleteFood(foodId: String) = remoteDataSource.deleteFood(foodId)
    override fun updateFood(foodId: String, name: String, calorie: Int) = remoteDataSource.updateFood(foodId, name, calorie)
}