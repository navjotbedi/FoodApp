package com.toptal.calorie.feature.login.domain.di

import com.toptal.calorie.feature.login.domain.usecase.LoginUseCase
import com.toptal.calorie.feature.login.domain.usecase.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
abstract class LoginDomainModule {

    @Binds
    internal abstract fun bindFoodUseCase(foodUseCaseImpl: LoginUseCaseImpl): LoginUseCase

}