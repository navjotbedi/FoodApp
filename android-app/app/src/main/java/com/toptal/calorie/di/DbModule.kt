package com.toptal.calorie.di

import android.content.Context
import androidx.room.Room
import com.toptal.calorie.core.CalorieDatabase
import com.toptal.calorie.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext application: Context) = Room.databaseBuilder(application.applicationContext, CalorieDatabase::class.java, DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun provideFoodDao(database: CalorieDatabase) = database.foodDao()
}