package com.toptal.calorie.feature.login.domain.usecase

import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(userId: String): Flow<LoginDomainModel>
}