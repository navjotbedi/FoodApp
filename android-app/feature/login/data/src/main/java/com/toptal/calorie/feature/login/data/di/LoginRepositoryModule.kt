package com.toptal.calorie.feature.login.data.di

import com.toptal.calorie.feature.login.data.LoginRepositoryImpl
import com.toptal.calorie.feature.login.data.local.LoginLocalDataSource
import com.toptal.calorie.feature.login.data.local.LoginLocalDataSourceImpl
import com.toptal.calorie.feature.login.data.remote.LoginRemoteDataSource
import com.toptal.calorie.feature.login.data.remote.LoginRemoteDataSourceImpl
import com.toptal.calorie.feature.login.domain.di.LoginDomainModule
import com.toptal.calorie.feature.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [LoginDomainModule::class])
@DisableInstallInCheck
abstract class LoginRepositoryModule {

    @Binds
    internal abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    internal abstract fun bindLoginRemoteRepository(loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource

    @Binds
    internal abstract fun bindLoginLocalRepository(loginLocalDataSourceImpl: LoginLocalDataSourceImpl): LoginLocalDataSource

}