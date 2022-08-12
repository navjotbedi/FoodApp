package com.toptal.calorie.feature.login.data.local

import com.toptal.calorie.core.utils.Constants.USER_NAME_PREF
import com.toptal.calorie.core.utils.Constants.USER_ROLE_PREF
import com.toptal.calorie.core.utils.Constants.USER_TOKEN_PREF
import com.toptal.calorie.feature.login.domain.entity.LoginDomainModel
import com.toptal.calorie.feature.login.domain.entity.USER_ROLE
import com.toptal.core.cache.preference.PreferenceCache
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LoginLocalDataSourceImpl @Inject constructor(
    private val preferenceCache: PreferenceCache
) : LoginLocalDataSource {

    override fun storeUserDetails(loginDomainModel: LoginDomainModel) = flow {
        preferenceCache.putString(USER_TOKEN_PREF, loginDomainModel.token)
        preferenceCache.putString(USER_NAME_PREF, loginDomainModel.name)
        preferenceCache.putString(USER_ROLE_PREF, loginDomainModel.role.name)
        emit(Unit)
    }

    override fun getLoggedInUserType() = preferenceCache.getString(USER_ROLE_PREF)
        .map { if (it.isNotEmpty()) USER_ROLE.toUserRole(it) else null }

}