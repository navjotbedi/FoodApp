package com.toptal.calorie.feature.food.ui.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class FoodUIModel(val id: String, val name: String, val calorie: String, val intakeDate: String? = null)