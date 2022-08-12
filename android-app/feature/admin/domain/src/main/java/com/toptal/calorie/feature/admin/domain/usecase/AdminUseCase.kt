package com.toptal.calorie.feature.admin.domain.usecase

import com.toptal.calorie.feature.admin.domain.entity.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface AdminUseCase {
    fun fetchUsers(): Flow<List<UserDomainModel>>
}