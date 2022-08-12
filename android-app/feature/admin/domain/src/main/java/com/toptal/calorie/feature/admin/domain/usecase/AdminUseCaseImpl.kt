package com.toptal.calorie.feature.admin.domain.usecase

import com.toptal.calorie.feature.admin.domain.repository.AdminRepository
import javax.inject.Inject

class AdminUseCaseImpl @Inject constructor(private val adminRepository: AdminRepository) : AdminUseCase {
    override fun fetchUsers() = adminRepository.fetchUsers()
    override fun fetchFoodItems(userId: String) = adminRepository.getFoodList(userId)
    override fun addFood(name: String, calorie: Int) = adminRepository.addFood(name, calorie)
    override fun deleteFood(foodId: String) = adminRepository.deleteFood(foodId)
    override fun updateFood(foodId: String, name: String, calorie: Int) = adminRepository.updateFood(foodId, name, calorie)
}