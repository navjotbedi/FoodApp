package com.toptal.calorie.feature.admin.data.remote.api

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.admin.data.GetFoodsQuery
import com.toptal.calorie.feature.admin.domain.entity.FoodDomainModel
import java.util.*
import javax.inject.Inject

class FoodApiMapper @Inject constructor() : Mapper<GetFoodsQuery.Food, FoodDomainModel>() {
    override fun map(value: GetFoodsQuery.Food) = FoodDomainModel(value.id, value.name, Date(), value.calorie)
}