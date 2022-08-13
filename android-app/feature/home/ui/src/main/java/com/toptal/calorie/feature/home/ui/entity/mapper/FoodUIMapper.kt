package com.toptal.calorie.feature.home.ui.entity.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.home.ui.entity.FoodUIModel
import java.text.SimpleDateFormat
import javax.inject.Inject

class FoodUIMapper @Inject constructor() : Mapper<FoodDomainModel, FoodUIModel>() {

    private val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy")

    override fun map(value: FoodDomainModel) = FoodUIModel(value.id, value.name, value.calorie.toString(), simpleDateFormat.format(value.intakeDate))
}