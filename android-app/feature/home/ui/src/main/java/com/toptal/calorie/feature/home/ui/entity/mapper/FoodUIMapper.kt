package com.toptal.calorie.feature.home.ui.entity.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.home.ui.entity.Food
import javax.inject.Inject

class FoodUIMapper @Inject constructor() : Mapper<FoodDomainModel, Food>() {
    override fun map(value: FoodDomainModel) = Food(value.id, value.name, value.calorie.toString(), value.intakeDate.toString())
}