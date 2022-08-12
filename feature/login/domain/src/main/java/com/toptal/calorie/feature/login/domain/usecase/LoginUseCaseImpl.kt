package com.toptal.calorie.feature.login.domain.usecase

import com.toptal.calorie.feature.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val loginRepository: LoginRepository) : LoginUseCase {
    override fun login(userId: String) = loginRepository.login(userId)
}