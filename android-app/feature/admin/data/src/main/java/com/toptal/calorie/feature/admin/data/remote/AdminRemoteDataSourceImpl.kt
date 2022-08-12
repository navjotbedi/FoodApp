package com.toptal.calorie.feature.admin.data.remote

import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.feature.admin.data.GetUsersQuery
import com.toptal.calorie.feature.admin.data.remote.api.UserApiMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AdminRemoteDataSourceImpl @Inject constructor(
    private val apiServiceCreator: ApiServiceCreator,
    private val userApiMapper: UserApiMapper
) : AdminRemoteDataSource {

    override fun fetchUsers() = apiServiceCreator.getApolloClient().query(GetUsersQuery()).toFlow()
        .map { response ->
            response.data?.users?.let { userList ->
                userList.mapNotNull { it?.let { userApiMapper.map(it) } }
            } ?: throw Exception(response.errors?.get(0)?.message)
        }
}