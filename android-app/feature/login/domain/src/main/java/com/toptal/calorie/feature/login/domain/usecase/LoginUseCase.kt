package com.toptal.calorie.feature.login.domain.usecase

import com.toptal.calorie.core.utils.USER_ROLE
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(userId: String): Flow<USER_ROLE>
    fun getLoggedInUserType(): Flow<USER_ROLE?>
}