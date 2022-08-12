package com.toptal.calorie.feature.home.data.remote

import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.feature.home.data.CreateFoodMutation
import com.toptal.calorie.feature.home.data.DeleteFoodMutation
import com.toptal.calorie.feature.home.data.FoodListQuery
import com.toptal.calorie.feature.home.data.UpdateFoodMutation
import com.toptal.calorie.feature.home.data.remote.api.FoodApiMapper
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

internal class FoodRemoteDataSourceImpl @Inject constructor(
    private val apiServiceCreator: ApiServiceCreator,
    private val foodApiMapper: FoodApiMapper
) : FoodRemoteDataSource {
    override fun getFoodList(userId: String) = apiServiceCreator.getApolloClient().query(FoodListQuery(userId)).toFlow()
        .map { response ->
            response.data?.users?.get(0)?.foods?.let { foodList ->
                foodList.mapNotNull { it?.let { foodApiMapper.map(it) } }
            } ?: throw Exception(response.errors?.get(0)?.message)
        }

    override fun saveFood(name: String, calorie: Int) = apiServiceCreator.getApolloClient().mutation(CreateFoodMutation(name, calorie)).toFlow()
        .map { response ->
            response.data?.createFood?.let {
                FoodDomainModel(it.id, it.name, Date(), it.calorie)
            }
                ?: throw Exception(response.errors?.get(0)?.message)
        }

    override fun deleteFood(foodId: String) = apiServiceCreator.getApolloClient().mutation(DeleteFoodMutation(foodId)).toFlow()
        .map { response ->
            if (response.hasErrors()) throw Exception(response.errors?.get(0)?.message)
            else Unit
        }

    override fun updateFood(foodId: String, name: String, calorie: Int) = apiServiceCreator.getApolloClient().mutation(UpdateFoodMutation(foodId, name, calorie)).toFlow()
        .map { response ->
            if (response.hasErrors()) throw Exception(response.errors?.get(0)?.message)
            else Unit
        }
}