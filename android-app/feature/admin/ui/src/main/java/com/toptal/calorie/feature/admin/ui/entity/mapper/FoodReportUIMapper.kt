package com.toptal.calorie.feature.admin.ui.entity.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.admin.domain.entity.FoodReportDomainModel
import com.toptal.calorie.feature.admin.ui.entity.FoodReportUIModel
import javax.inject.Inject

class FoodReportUIMapper @Inject constructor() : Mapper<FoodReportDomainModel, FoodReportUIModel>() {
    override fun map(value: FoodReportDomainModel) = with(value) { FoodReportUIModel(currentWeekCalorieAvg, lastWeekCalorieAvg) }

}