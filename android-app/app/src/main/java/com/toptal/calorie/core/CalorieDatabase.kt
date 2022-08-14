package com.toptal.calorie.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.toptal.calorie.feature.food.data.local.db.dao.FoodDao
import com.toptal.calorie.feature.food.data.local.db.entity.FoodEntity
import com.toptal.calorie.utils.DateStringConverter

@Database(entities = [FoodEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateStringConverter::class)
abstract class CalorieDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}