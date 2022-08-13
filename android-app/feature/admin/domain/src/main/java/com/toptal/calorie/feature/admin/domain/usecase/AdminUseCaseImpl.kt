package com.toptal.calorie.feature.admin.domain.usecase

import com.toptal.calorie.feature.admin.domain.repository.AdminRepository
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class AdminUseCaseImpl @Inject constructor(private val adminRepository: AdminRepository) : AdminUseCase {
    override fun fetchUsers() = adminRepository.fetchUsers()
    override fun fetchReport() = adminRepository.fetchFoodReport().zip(adminRepository.fetchAvgCaloriePerUser(), { foodReportDomainModel, avgCaloriePerUserDomainModels ->
        Pair(foodReportDomainModel, avgCaloriePerUserDomainModels)
    })
}