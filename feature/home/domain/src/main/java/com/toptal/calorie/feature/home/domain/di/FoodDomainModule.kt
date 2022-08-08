package com.toptal.calorie.feature.home.domain.di

import com.toptal.calorie.feature.home.domain.usecase.FoodUseCase
import com.toptal.calorie.feature.home.domain.usecase.FoodUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
abstract class FoodDomainModule {

    @Binds
    internal abstract fun bindFoodUseCase(foodUseCaseImpl: FoodUseCaseImpl): FoodUseCase

}