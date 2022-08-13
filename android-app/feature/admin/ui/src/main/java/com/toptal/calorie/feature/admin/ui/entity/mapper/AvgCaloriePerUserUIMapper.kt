package com.toptal.calorie.feature.admin.ui.entity.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.admin.domain.entity.AvgCaloriePerUserDomainModel
import com.toptal.calorie.feature.admin.ui.entity.AvgCaloriePerUserUIModel
import javax.inject.Inject

class AvgCaloriePerUserUIMapper @Inject constructor() : Mapper<AvgCaloriePerUserDomainModel, AvgCaloriePerUserUIModel>() {
    override fun map(value: AvgCaloriePerUserDomainModel) = AvgCaloriePerUserUIModel(value.calorie, value.userDomainModel.name)
}