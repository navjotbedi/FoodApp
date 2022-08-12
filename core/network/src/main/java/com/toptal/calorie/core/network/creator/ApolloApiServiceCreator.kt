package com.toptal.calorie.core.network.creator

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.toptal.calorie.core.network.AppConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

internal class ApolloApiServiceCreator @Inject constructor(private val appConfig: AppConfig) : ApiServiceCreator {

    private fun getOkHttp() = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    }.build()

    override fun getApolloClient() = ApolloClient.Builder()
        .serverUrl(appConfig.baseUrl)
        .okHttpClient(getOkHttp())
        .build()

}