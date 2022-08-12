package com.toptal.calorie.feature.home.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class FoodEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val calorie: Int,
    val intakeDate: Date
)