package com.toptal.calorie.feature.admin.ui.di

import com.toptal.calorie.feature.admin.data.di.AdminRepositoryModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [AdminFactoryModule::class])
@DisableInstallInCheck
object AdminModule

@Module(includes = [AdminRepositoryModule::class])
@InstallIn(ViewModelComponent::class)
internal object AdminFactoryModule