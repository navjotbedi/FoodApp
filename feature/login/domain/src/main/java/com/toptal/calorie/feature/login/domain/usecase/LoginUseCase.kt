package com.toptal.calorie.feature.login.domain.usecase

import com.toptal.calorie.feature.login.domain.entity.USER_ROLE
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(userId: String): Flow<Unit>
    fun getLoggedInUserType(): Flow<USER_ROLE?>
}