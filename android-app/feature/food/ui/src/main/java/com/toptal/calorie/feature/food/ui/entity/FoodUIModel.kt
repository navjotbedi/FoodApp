package com.toptal.calorie.feature.food.ui.entity

import kotlinx.serialization.Serializable

@Serializable
data class FoodUIModel(val id: String, val name: String, val calorie: String, val intakeDate: String? = null)