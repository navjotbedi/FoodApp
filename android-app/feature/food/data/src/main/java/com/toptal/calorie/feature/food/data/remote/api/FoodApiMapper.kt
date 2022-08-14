package com.toptal.calorie.feature.food.data.remote.api

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.food.data.FoodListQuery
import com.toptal.calorie.feature.food.domain.entity.FoodDomainModel
import java.sql.Time
import javax.inject.Inject

class FoodApiMapper @Inject constructor() : Mapper<FoodListQuery.Food, FoodDomainModel>() {
    override fun map(value: FoodListQuery.Food) = FoodDomainModel(value.id, value.name, Time(value.intakeDate.toLong()), value.calorie)
}