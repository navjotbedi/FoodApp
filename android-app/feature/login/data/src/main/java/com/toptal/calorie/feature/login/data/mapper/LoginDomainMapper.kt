package com.toptal.calorie.feature.login.data.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.login.data.LoginMutation
import com.toptal.calorie.feature.login.data.type.Role
import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import com.toptal.calorie.feature.login.domain.entity.USER_ROLE
import javax.inject.Inject

class LoginDomainMapper @Inject constructor() : Mapper<LoginMutation.Data, LoginDomainModel>() {
    override fun map(value: LoginMutation.Data) = LoginDomainModel(
        value.login.token, value.login.user.name, when (value.login.user.role) {
            Role.ADMIN -> USER_ROLE.ADMIN
            else -> USER_ROLE.USER
        }
    )
}