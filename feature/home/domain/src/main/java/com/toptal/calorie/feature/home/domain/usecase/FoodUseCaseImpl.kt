package com.toptal.calorie.feature.home.domain.usecase

import androidx.paging.PagingData
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import com.toptal.calorie.feature.home.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodUseCaseImpl @Inject constructor(foodRepository: FoodRepository) : FoodUseCase {
    override fun fetchFoodItems(): Flow<PagingData<FoodDomainModel>> {
        TODO("Not yet implemented")
    }

    override fun saveFood(foodDomainModel: FoodDomainModel): Flow<Unit> {
        TODO("Not yet implemented")
    }
}