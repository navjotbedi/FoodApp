package com.toptal.calorie.feature.login.domain.entity

import com.toptal.calorie.core.utils.USER_ROLE

data class LoginDomainModel(val token: String, val name: String, val role: USER_ROLE)