package com.toptal.calorie.feature.food.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toptal.calorie.feature.food.data.local.db.entity.FoodEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface FoodDao {

    @Query("SELECT * FROM FoodEntity ORDER BY intakeDate DESC")
    fun getFoodList(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM FoodEntity WHERE intakeDate >= :startDate AND intakeDate <= :endDate ORDER BY intakeDate DESC")
    fun getFoodList(startDate: Date, endDate: Date): Flow<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFoodList(foodList: List<FoodEntity>)

    @Query("DELETE FROM FoodEntity")
    fun nukeTable()
}