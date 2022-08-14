package com.toptal.calorie.feature.home.domain.usecase

import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import java.util.*
import javax.inject.Inject

class FoodUseCaseImpl @Inject constructor(private val foodRepository: FoodRepository) : FoodUseCase {
    override fun fetchFoodList(startDate: Date?, endDate: Date?) = foodRepository.fetchFoodList(startDate, endDate)
    override fun clearLocalCache() = foodRepository.clearLocalCache()
    override fun saveFoodList(userId: String?) = foodRepository.saveFoodList(userId)
    override fun saveFood(name: String, calorie: Int, userId: String?) = foodRepository.saveFood(name, calorie, userId)
    override fun deleteFood(foodId: String) = foodRepository.deleteFood(foodId)
    override fun updateFood(foodId: String, name: String, calorie: Int) = foodRepository.updateFood(foodId, name, calorie)
}