package com.toptal.calorie.core.utils

abstract class Mapper<T1, T2> {
    abstract fun map(value: T1): T2
    open fun reverseMap(value: T2): T1 = throw Exception("Unhandled return type")
}