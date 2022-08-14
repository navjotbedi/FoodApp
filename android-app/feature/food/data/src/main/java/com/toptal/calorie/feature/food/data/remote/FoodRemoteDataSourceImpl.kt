package com.toptal.calorie.feature.food.data.remote

import com.apollographql.apollo3.api.Optional
import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.feature.food.data.CreateFoodMutation
import com.toptal.calorie.feature.food.data.DeleteFoodMutation
import com.toptal.calorie.feature.food.data.FoodListQuery
import com.toptal.calorie.feature.food.data.UpdateFoodMutation
import com.toptal.calorie.feature.food.data.remote.api.FoodApiMapper
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

internal class FoodRemoteDataSourceImpl @Inject constructor(
    private val apiServiceCreator: ApiServiceCreator,
    private val foodApiMapper: FoodApiMapper
) : FoodRemoteDataSource {
    override fun getFoodList(userId: String?, startDate: Date?, endDate: Date?) = apiServiceCreator.getApolloClient().query(FoodListQuery(Optional.presentIfNotNull(userId))).toFlow()
        .map { response ->
            response.data?.foods?.let { foodList ->
                foodList.mapNotNull { it?.let { foodApiMapper.map(it) } }
            } ?: throw Exception(response.errors?.get(0)?.message)
        }

    override fun saveFood(name: String, calorie: Int, userId: String?) = apiServiceCreator.getApolloClient().mutation(CreateFoodMutation(Optional.presentIfNotNull(userId), name, calorie)).toFlow()
        .map { it.data?.createFood?.let {} ?: throw Exception(it.errors?.get(0)?.message) }

    override fun deleteFood(foodId: String) = apiServiceCreator.getApolloClient().mutation(DeleteFoodMutation(foodId)).toFlow()
        .map { if (it.hasErrors()) throw Exception(it.errors?.get(0)?.message) }

    override fun updateFood(foodId: String, name: String, calorie: Int) = apiServiceCreator.getApolloClient().mutation(UpdateFoodMutation(foodId, name, calorie)).toFlow()
        .map { if (it.hasErrors()) throw Exception(it.errors?.get(0)?.message) }
}