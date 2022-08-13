package com.toptal.calorie.feature.admin.data.remote.api

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.admin.data.GetFoodReportQuery
import com.toptal.calorie.feature.admin.domain.entity.FoodReportDomainModel
import javax.inject.Inject

class FoodReportApiMapper @Inject constructor() : Mapper<GetFoodReportQuery.FoodReport, FoodReportDomainModel>() {
    override fun map(value: GetFoodReportQuery.FoodReport) = with(value) { FoodReportDomainModel(currentWeek, lastWeek) }
}