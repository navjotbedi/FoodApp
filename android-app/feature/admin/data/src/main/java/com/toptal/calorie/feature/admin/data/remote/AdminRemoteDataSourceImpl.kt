package com.toptal.calorie.feature.admin.data.remote

import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.feature.admin.data.GetAvgCaloriePerUserQuery
import com.toptal.calorie.feature.admin.data.GetFoodReportQuery
import com.toptal.calorie.feature.admin.data.GetUsersQuery
import com.toptal.calorie.feature.admin.data.remote.api.AvgCaloriePerUserApiMapper
import com.toptal.calorie.feature.admin.data.remote.api.FoodReportApiMapper
import com.toptal.calorie.feature.admin.data.remote.api.UserApiMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AdminRemoteDataSourceImpl @Inject constructor(
    private val apiServiceCreator: ApiServiceCreator,
    private val userApiMapper: UserApiMapper,
    private val foodReportApiMapper: FoodReportApiMapper,
    private val avgCaloriePerUserApiMapper: AvgCaloriePerUserApiMapper
) : AdminRemoteDataSource {

    override fun fetchUsers() = apiServiceCreator.getApolloClient().query(GetUsersQuery()).toFlow()
        .map { response ->
            response.data?.users?.let { userList ->
                userList.mapNotNull { it?.let { userApiMapper.map(it) } }
            } ?: throw Exception(response.errors?.get(0)?.message)
        }

    override fun fetchFoodReport() = apiServiceCreator.getApolloClient().query(GetFoodReportQuery()).toFlow()
        .map { response ->
            response.data?.foodReport?.let {
                foodReportApiMapper.map(it)
            } ?: throw Exception(response.errors?.get(0)?.message)
        }

    override fun fetchAvgCaloriePerUser() = apiServiceCreator.getApolloClient().query(GetAvgCaloriePerUserQuery()).toFlow()
        .map { response ->
            response.data?.avgCaloriesPerUser?.let { list ->
                list.mapNotNull { it?.let { avgCaloriePerUserApiMapper.map(it) } }
            } ?: throw Exception(response.errors?.get(0)?.message)
        }
}