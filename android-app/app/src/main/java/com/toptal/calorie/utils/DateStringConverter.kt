package com.toptal.calorie.utils

import androidx.room.TypeConverter
import com.toptal.calorie.utils.Constants.DATE_FORMAT_YYYY_MM_DD_hh_mm_ss
import java.text.SimpleDateFormat
import java.util.*

class DateStringConverter {

    @TypeConverter
    fun fromDateString(value: String?): Date? {
        return value?.let { SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_hh_mm_ss, Locale.ENGLISH).parse(value) }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return date?.let { SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_hh_mm_ss, Locale.ENGLISH).format(date) }
    }

}