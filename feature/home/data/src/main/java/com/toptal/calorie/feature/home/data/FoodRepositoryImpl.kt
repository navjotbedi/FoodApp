package com.toptal.calorie.feature.home.data

import androidx.paging.PagingData
import com.toptal.calorie.feature.home.data.local.FoodLocalDataSource
import com.toptal.calorie.feature.home.data.remote.FoodRemoteDataSource
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class FoodRepositoryImpl @Inject constructor(
    remoteDataSource: FoodRemoteDataSource,
    localDataSource: FoodLocalDataSource
) : FoodRepository {
    override fun getFoodList(): Flow<PagingData<FoodDomainModel>> {
        TODO("Not yet implemented")
    }
}