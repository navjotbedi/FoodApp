package com.toptal.calorie.feature.login.domain.repository

import com.toptal.calorie.feature.login.domain.entity.USER_ROLE
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(userId: String): Flow<Unit>
    fun getLoggedInUserType(): Flow<USER_ROLE?>
}