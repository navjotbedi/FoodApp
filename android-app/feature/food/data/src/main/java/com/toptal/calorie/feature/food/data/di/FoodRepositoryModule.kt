package com.toptal.calorie.feature.food.data.di

import com.toptal.calorie.feature.food.data.FoodRepositoryImpl
import com.toptal.calorie.feature.food.data.local.FoodLocalDataSource
import com.toptal.calorie.feature.food.data.local.FoodLocalDataSourceImpl
import com.toptal.calorie.feature.food.data.remote.FoodRemoteDataSource
import com.toptal.calorie.feature.food.data.remote.FoodRemoteDataSourceImpl
import com.toptal.calorie.feature.food.domain.di.FoodDomainModule
import com.toptal.calorie.feature.food.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [FoodDomainModule::class])
@DisableInstallInCheck
abstract class FoodRepositoryModule {

    @Binds
    internal abstract fun bindFoodRepository(foodRepositoryImpl: FoodRepositoryImpl): FoodRepository

    @Binds
    internal abstract fun bindFoodLocalRepository(foodLocalDataSourceImpl: FoodLocalDataSourceImpl): FoodLocalDataSource

    @Binds
    internal abstract fun bindFoodRemoteRepository(foodRemoteDataSourceImpl: FoodRemoteDataSourceImpl): FoodRemoteDataSource

}