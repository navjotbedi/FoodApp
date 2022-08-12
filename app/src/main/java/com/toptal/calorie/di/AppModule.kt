package com.toptal.calorie.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.toptal.calorie.core.network.AppConfig
import com.toptal.calorie.core.network.di.RemoteModule
import com.toptal.calorie.utils.Constants.BASE_URL
import com.toptal.calorie.utils.Constants.DATA_STORE_NAME
import com.toptal.core.cache.di.CacheModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RemoteModule::class, CacheModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppConfig(): AppConfig = AppConfig(BASE_URL)

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore
}