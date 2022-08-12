package com.toptal.calorie.feature.login.ui.di

import com.toptal.calorie.feature.login.data.di.LoginRepositoryModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [LoginFactoryModule::class])
@DisableInstallInCheck
object LoginModule

@Module(includes = [LoginRepositoryModule::class])
@InstallIn(ViewModelComponent::class)
internal object LoginFactoryModule