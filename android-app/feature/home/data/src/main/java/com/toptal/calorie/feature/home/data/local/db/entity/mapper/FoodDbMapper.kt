package com.toptal.calorie.feature.home.data.local.db.entity.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.home.data.local.db.entity.FoodEntity
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import javax.inject.Inject

class FoodDbMapper @Inject constructor() : Mapper<FoodEntity, FoodDomainModel>() {
    override fun map(value: FoodEntity) = with(value) { FoodDomainModel(id, name, intakeDate, calorie) }
    override fun reverseMap(value: FoodDomainModel) = with(value) { FoodEntity(id, name, calorie, intakeDate) }
}