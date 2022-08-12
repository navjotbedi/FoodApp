package com.toptal.calorie.feature.admin.data.di

import com.toptal.calorie.feature.admin.data.AdminRepositoryImpl
import com.toptal.calorie.feature.admin.data.remote.AdminRemoteDataSource
import com.toptal.calorie.feature.admin.data.remote.AdminRemoteDataSourceImpl
import com.toptal.calorie.feature.admin.domain.di.AdminDomainModule
import com.toptal.calorie.feature.admin.domain.repository.AdminRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [AdminDomainModule::class])
@DisableInstallInCheck
abstract class AdminRepositoryModule {

    @Binds
    internal abstract fun bindAdminRepository(adminRepositoryImpl: AdminRepositoryImpl): AdminRepository

    @Binds
    internal abstract fun bindAdminRemoteRepository(adminRemoteDataSourceImpl: AdminRemoteDataSourceImpl): AdminRemoteDataSource

}