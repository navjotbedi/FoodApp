package com.toptal.calorie.feature.home.domain.usecase

import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import javax.inject.Inject

class FoodUseCaseImpl @Inject constructor(private val foodRepository: FoodRepository) : FoodUseCase {
    override fun fetchFoodItems(userId: String?) = foodRepository.getFoodList(userId)
    override fun saveFood(name: String, calorie: Int) = foodRepository.saveFood(name, calorie)
}