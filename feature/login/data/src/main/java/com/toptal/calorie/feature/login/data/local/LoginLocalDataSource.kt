package com.toptal.calorie.feature.login.data.local

import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import com.toptal.calorie.feature.login.domain.entity.USER_ROLE
import kotlinx.coroutines.flow.Flow

interface LoginLocalDataSource {
    fun storeUserDetails(loginDomainModel: LoginDomainModel): Flow<Unit>
    fun getLoggedInUserType(): Flow<USER_ROLE?>
}