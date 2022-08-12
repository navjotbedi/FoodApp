package com.toptal.calorie.feature.admin.domain.di

import com.toptal.calorie.feature.admin.domain.usecase.AdminUseCase
import com.toptal.calorie.feature.admin.domain.usecase.AdminUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
abstract class AdminDomainModule {

    @Binds
    internal abstract fun bindFoodUseCase(foodUseCaseImpl: AdminUseCaseImpl): AdminUseCase

}