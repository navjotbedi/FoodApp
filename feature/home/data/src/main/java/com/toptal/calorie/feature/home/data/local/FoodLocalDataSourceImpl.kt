package com.toptal.calorie.feature.home.data.local

import com.toptal.calorie.core.utils.Constants
import com.toptal.core.cache.preference.PreferenceCache
import javax.inject.Inject

class FoodLocalDataSourceImpl @Inject constructor(private val preferenceCache: PreferenceCache) : FoodLocalDataSource {

    override fun getUserToken() = preferenceCache.getString(Constants.USER_TOKEN_PREF)

}