package com.toptal.calorie.feature.login.data.remote

import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import kotlinx.coroutines.flow.Flow

interface LoginRemoteDataSource {
    fun login(userId: String): Flow<LoginDomainModel>
}