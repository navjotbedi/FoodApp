package com.toptal.calorie.feature.login.domain.repository

import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(userId: String): Flow<LoginDomainModel>
}