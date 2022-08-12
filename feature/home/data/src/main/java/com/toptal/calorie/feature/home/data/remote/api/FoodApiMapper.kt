package com.toptal.calorie.feature.home.data.remote.api

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.home.data.FoodListQuery
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import java.util.*
import javax.inject.Inject

class FoodApiMapper @Inject constructor() : Mapper<FoodListQuery.Food, FoodDomainModel>() {
    override fun map(value: FoodListQuery.Food) = FoodDomainModel(value.id, value.name, Date(), value.calorie)
}