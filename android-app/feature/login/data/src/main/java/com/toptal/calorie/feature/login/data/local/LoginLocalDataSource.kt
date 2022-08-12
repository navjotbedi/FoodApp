package com.toptal.calorie.feature.login.data.local

import com.toptal.calorie.core.utils.USER_ROLE
import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import kotlinx.coroutines.flow.Flow

interface LoginLocalDataSource {
    fun storeUserDetails(loginDomainModel: LoginDomainModel): Flow<USER_ROLE>
    fun getLoggedInUserType(): Flow<USER_ROLE?>
}