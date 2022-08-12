package com.toptal.calorie.feature.home.data

import com.toptal.calorie.feature.home.data.local.FoodLocalDataSource
import com.toptal.calorie.feature.home.data.remote.FoodRemoteDataSource
import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FoodRepositoryImpl @Inject constructor(
    private val remoteDataSource: FoodRemoteDataSource,
    private val localDataSource: FoodLocalDataSource
) : FoodRepository {
    @OptIn(FlowPreview::class)
    override fun getFoodList() = localDataSource.getUserToken().flatMapConcat { remoteDataSource.getFoodList(it) }
    override fun saveFood(name: String, calorie: Int) = remoteDataSource.saveFood(name, calorie)
        .map { }
}