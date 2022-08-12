package com.toptal.calorie.feature.admin.data.remote

import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.feature.admin.data.*
import com.toptal.calorie.feature.admin.data.remote.api.FoodApiMapper
import com.toptal.calorie.feature.admin.data.remote.api.UserApiMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AdminRemoteDataSourceImpl @Inject constructor(
    private val apiServiceCreator: ApiServiceCreator,
    private val foodApiMapper: FoodApiMapper,
    private val userApiMapper: UserApiMapper
) : AdminRemoteDataSource {

    override fun fetchUsers() = apiServiceCreator.getApolloClient().query(GetUsersQuery()).toFlow()
        .map { response ->
            response.data?.users?.let { userList ->
                userList.mapNotNull { it?.let { userApiMapper.map(it) } }
            } ?: throw Exception(response.errors?.get(0)?.message)
        }

    override fun getFoodList(userId: String) = apiServiceCreator.getApolloClient().query(GetFoodsQuery(userId)).toFlow()
        .map { response ->
            response.data?.users?.get(0)?.foods?.let { foodList ->
                foodList.mapNotNull { it?.let { foodApiMapper.map(it) } }
            } ?: throw Exception(response.errors?.get(0)?.message)
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

    override fun addFood(name: String, calorie: Int) = apiServiceCreator.getApolloClient().mutation(CreateFoodMutation(name, calorie)).toFlow()
        .map { response ->
            if (response.hasErrors()) throw Exception(response.errors?.get(0)?.message)
            else Unit
        }
}