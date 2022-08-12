package com.toptal.calorie.feature.home.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.toptal.calorie.core.utils.Constants
import com.toptal.calorie.feature.home.data.local.db.dao.FoodDao
import com.toptal.calorie.feature.home.data.local.db.entity.mapper.FoodDbMapper
import com.toptal.calorie.feature.home.domain.entity.FoodDomainModel
import com.toptal.core.cache.preference.PreferenceCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoodLocalDataSourceImpl @Inject constructor(
    private val preferenceCache: PreferenceCache,
    private val foodDao: FoodDao,
    private val foodDbMapper: FoodDbMapper
) : FoodLocalDataSource {

    override fun getUserToken() = preferenceCache.getString(Constants.USER_TOKEN_PREF)
    override fun getFoodList() = Pager(PagingConfig(pageSize = 10)) { foodDao.getFoodList() }
        .flow
        .map { pagingData ->
            pagingData.map { withContext(Dispatchers.IO) { foodDbMapper.map(it) } }
        }

    override fun saveFoodList(foodList: List<FoodDomainModel>) = flow {
        emit(foodList.map {
            foodDbMapper.reverseMap(it)
        })
    }.map {
        foodDao.saveFoodList(it)
    }

}