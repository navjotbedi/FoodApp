package com.toptal.calorie.feature.home.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toptal.calorie.feature.home.data.local.db.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * FROM FoodEntity ORDER BY intakeDate DESC")
    fun getFoodList(): Flow<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFoodList(foodList: List<FoodEntity>)

    @Query("DELETE FROM FoodEntity")
    fun nukeTable()
}