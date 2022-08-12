package com.toptal.calorie.feature.login.domain.repository

import com.toptal.calorie.core.utils.USER_ROLE
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(userId: String): Flow<USER_ROLE>
    fun getLoggedInUserType(): Flow<USER_ROLE?>
}