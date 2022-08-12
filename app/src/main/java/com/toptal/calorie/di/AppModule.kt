package com.toptal.calorie.di

import com.toptal.calorie.core.network.AppConfig
import com.toptal.calorie.core.network.di.RemoteModule
import com.toptal.calorie.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RemoteModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppConfig(): AppConfig = AppConfig(BASE_URL)
}