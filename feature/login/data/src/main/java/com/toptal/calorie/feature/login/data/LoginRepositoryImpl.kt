package com.toptal.calorie.feature.login.data

import com.toptal.calorie.feature.login.data.local.LoginLocalDataSource
import com.toptal.calorie.feature.login.data.remote.LoginRemoteDataSource
import com.toptal.calorie.feature.login.domain.repository.LoginRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource
) : LoginRepository {
    @OptIn(FlowPreview::class)
    override fun login(userId: String) = remoteDataSource.login(userId).flatMapConcat { localDataSource.storeUserDetails(it) }
    override fun getLoggedInUserType() = localDataSource.getLoggedInUserType()
}