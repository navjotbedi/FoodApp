package com.toptal.calorie.feature.home.data.local

import com.toptal.calorie.core.utils.Constants
import com.toptal.calorie.feature.home.data.local.db.dao.FoodDao
import com.toptal.calorie.feature.home.data.local.db.entity.mapper.FoodDbMapper
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import com.toptal.core.cache.preference.PreferenceCache
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FoodLocalDataSourceImpl @Inject constructor(
    private val preferenceCache: PreferenceCache,
    private val foodDao: FoodDao,
    private val foodDbMapper: FoodDbMapper
) : FoodLocalDataSource {

    override fun getUserToken() = preferenceCache.getString(Constants.USER_TOKEN_PREF)
    override fun getFoodList() = foodDao.getFoodList().map { it.map { foodEntity -> foodDbMapper.map(foodEntity) } }

    override fun saveFoodList(foodList: List<FoodDomainModel>) = flow {
        emit(foodList.map {
            foodDbMapper.reverseMap(it)
        })
    }.map {
        foodDao.saveFoodList(it)
    }

    override fun clearTable() = flow { emit(foodDao.nukeTable()) }
}