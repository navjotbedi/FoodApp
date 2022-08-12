package com.toptal.calorie.core.utils

enum class USER_ROLE {
    ADMIN, USER;

    companion object {
        fun toUserRole(userRoleString: String) = when (userRoleString) {
            "ADMIN" -> ADMIN
            else -> USER
        }
    }
}