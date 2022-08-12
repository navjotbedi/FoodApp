package com.toptal.calorie.feature.login.data.remote

import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.feature.login.data.LoginMutation
import com.toptal.calorie.feature.login.data.mapper.LoginDomainMapper
import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LoginRemoteDataSourceImpl @Inject constructor(
    private val apiServiceCreator: ApiServiceCreator,
    private val loginDomainMapper: LoginDomainMapper
) : LoginRemoteDataSource {

    override fun login(userId: String): Flow<LoginDomainModel> {
        return apiServiceCreator.getApolloClient().mutation(LoginMutation(userId)).toFlow()
            .map { response ->
                response.data?.let {
                    return@map loginDomainMapper.map(it)
                } ?: throw Exception(response.errors?.get(0)?.message)
            }
    }

}