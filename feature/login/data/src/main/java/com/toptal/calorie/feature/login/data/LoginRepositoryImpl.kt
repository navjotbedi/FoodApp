package com.toptal.calorie.feature.login.data

import com.toptal.calorie.feature.login.data.remote.LoginRemoteDataSource
import com.toptal.calorie.feature.login.domain.repository.LoginRepository
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    override fun login(userId: String) = remoteDataSource.login(userId)
}