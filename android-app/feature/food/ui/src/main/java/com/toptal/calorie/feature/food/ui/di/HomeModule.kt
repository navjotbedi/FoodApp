package com.toptal.calorie.feature.food.ui.di

import com.toptal.calorie.feature.food.data.di.FoodRepositoryModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [FoodFactoryModule::class])
@DisableInstallInCheck
object FoodModule

@Module(includes = [FoodRepositoryModule::class])
@InstallIn(ViewModelComponent::class)
internal object FoodFactoryModule