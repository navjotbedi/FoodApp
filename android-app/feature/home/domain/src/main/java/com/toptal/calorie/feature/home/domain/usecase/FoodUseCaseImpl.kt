package com.toptal.calorie.feature.home.domain.usecase

import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import javax.inject.Inject

class FoodUseCaseImpl @Inject constructor(private val foodRepository: FoodRepository) : FoodUseCase {
    override fun fetchFoodList() = foodRepository.fetchFoodList()
    override fun saveFoodList(userId: String?) = foodRepository.saveFoodList(userId)
    override fun saveFood(name: String, calorie: Int) = foodRepository.saveFood(name, calorie)
    override fun deleteFood(foodId: String) = foodRepository.deleteFood(foodId)
    override fun updateFood(foodId: String, name: String, calorie: Int) = foodRepository.updateFood(foodId, name, calorie)
}