package com.toptal.calorie.core.network.creator

import com.apollographql.apollo3.ApolloClient

interface ApiServiceCreator {
    fun getApolloClient(): ApolloClient
}