package com.toptal.calorie.feature.admin.ui.entity.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import com.toptal.calorie.feature.admin.ui.entity.User
import javax.inject.Inject

class UserUIMapper @Inject constructor() : Mapper<UserDomainModel, User>() {
    override fun map(value: UserDomainModel) = User(value.id, value.name)
}