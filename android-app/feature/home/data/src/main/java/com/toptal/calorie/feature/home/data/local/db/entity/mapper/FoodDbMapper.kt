package com.toptal.calorie.feature.home.data.local.db.entity.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.home.data.local.db.entity.FoodDbModel
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import javax.inject.Inject

class FoodDbMapper @Inject constructor() : Mapper<FoodDbModel, FoodDomainModel>() {
    override fun map(value: FoodDbModel): FoodDomainModel {
        TODO("Not yet implemented")
    }
}