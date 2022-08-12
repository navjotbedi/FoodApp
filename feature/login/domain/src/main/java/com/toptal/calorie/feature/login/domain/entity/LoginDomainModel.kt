package com.toptal.calorie.feature.login.domain.entity

data class LoginDomainModel(val token: String, val name: String, val role: USER_ROLE)

enum class USER_ROLE {
    ADMIN, USER;

    companion object {
        fun toUserRole(userRoleString: String) = when (userRoleString) {
            "ADMIN" -> ADMIN
            else -> USER
        }
    }
}