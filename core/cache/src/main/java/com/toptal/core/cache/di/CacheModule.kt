package com.toptal.core.cache.di

import com.toptal.core.cache.preference.DataStorePreferenceCacheImpl
import com.toptal.core.cache.preference.PreferenceCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun provideDataStorePreferenceCache(dataStorePreferenceCacheImpl: DataStorePreferenceCacheImpl): PreferenceCache

}