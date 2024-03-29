package com.toptal.calorie.feature.food.data.local

import com.toptal.calorie.core.utils.Constants
import com.toptal.calorie.feature.food.data.local.db.dao.FoodDao
import com.toptal.calorie.feature.food.data.local.db.entity.mapper.FoodDbMapper
import com.toptal.calorie.feature.food.domain.entity.FoodDomainModel
import com.toptal.core.cache.preference.PreferenceCache
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class FoodLocalDataSourceImpl @Inject constructor(
    private val preferenceCache: PreferenceCache,
    private val foodDao: FoodDao,
    private val foodDbMapper: FoodDbMapper
) : FoodLocalDataSource {

    override fun getUserToken() = preferenceCache.getString(Constants.USER_TOKEN_PREF)
    override fun getFoodList(startDate: Date?, endDate: Date?) = with(foodDao) {
        (if (startDate != null && endDate != null)
            getFoodList(startDate, endDate)
        else
            getFoodList()).map { it.map { foodEntity -> foodDbMapper.map(foodEntity) } }
    }

    override fun saveFoodList(foodList: List<FoodDomainModel>) = flow {
        emit(foodList.map {
            foodDbMapper.reverseMap(it)
        })
    }
        .map {
            foodDao.nukeTable()
            foodDao.saveFoodList(it)
        }

    override fun clearTable() = flow { emit(foodDao.nukeTable()) }
}