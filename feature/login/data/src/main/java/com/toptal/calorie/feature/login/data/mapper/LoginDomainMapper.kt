package com.toptal.calorie.feature.login.data.mapper

import com.toptal.calorie.core.utils.Mapper
import com.toptal.calorie.feature.login.data.LoginMutation
import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import javax.inject.Inject

class LoginDomainMapper @Inject constructor() : Mapper<LoginMutation.Data, LoginDomainModel>() {
    override fun map(value: LoginMutation.Data) = LoginDomainModel(value.login.id, value.login.name)
}