package com.toptal.calorie.feature.admin.data.remote.api

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.admin.data.GetAvgCaloriePerUserQuery
import com.toptal.calorie.feature.admin.domain.entity.AvgCaloriePerUserDomainModel
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import javax.inject.Inject

class AvgCaloriePerUserApiMapper @Inject constructor() : Mapper<GetAvgCaloriePerUserQuery.AvgCaloriesPerUser, AvgCaloriePerUserDomainModel>() {
    override fun map(value: GetAvgCaloriePerUserQuery.AvgCaloriesPerUser) = with(value) { AvgCaloriePerUserDomainModel(avg_cal, UserDomainModel(user.id, user.name)) }
}