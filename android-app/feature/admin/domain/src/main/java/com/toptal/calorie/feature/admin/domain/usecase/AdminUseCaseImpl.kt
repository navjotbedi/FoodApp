package com.toptal.calorie.feature.admin.domain.usecase

import com.toptal.calorie.feature.admin.domain.repository.AdminRepository
import javax.inject.Inject

class AdminUseCaseImpl @Inject constructor(private val adminRepository: AdminRepository) : AdminUseCase {
    override fun fetchUsers() = adminRepository.fetchUsers()
}