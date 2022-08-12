package com.toptal.calorie.core.network.di

import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.core.network.creator.ApolloApiServiceCreator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {
    @Binds
    internal abstract fun bindServiceCreator(retrofitServiceCreator: ApolloApiServiceCreator): ApiServiceCreator
}