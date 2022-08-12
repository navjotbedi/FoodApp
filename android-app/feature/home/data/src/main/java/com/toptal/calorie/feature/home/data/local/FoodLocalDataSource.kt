package com.toptal.calorie.feature.home.data.local

import kotlinx.coroutines.flow.Flow

interface FoodLocalDataSource {
    fun getUserToken(): Flow<String>
}