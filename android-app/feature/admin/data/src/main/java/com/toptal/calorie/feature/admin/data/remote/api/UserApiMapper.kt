package com.toptal.calorie.feature.admin.data.remote.api

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.admin.data.GetUsersQuery
import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import javax.inject.Inject

class UserApiMapper @Inject constructor() : Mapper<GetUsersQuery.User, UserDomainModel>() {
    override fun map(value: GetUsersQuery.User) = UserDomainModel(value.id, value.name)
}