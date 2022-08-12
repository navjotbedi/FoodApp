package com.toptal.calorie.feature.home.data.remote

import com.toptal.calorie.core.network.creator.ApiServiceCreator
import com.toptal.calorie.feature.home.data.UserQuery
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FoodRemoteDataSourceImpl @Inject constructor(val apiServiceCreator: ApiServiceCreator) : FoodRemoteDataSource {

    fun fetch() {
        apiServiceCreator.getApolloClient().query(UserQuery()).toFlow()
            .map {
            }
    }

}