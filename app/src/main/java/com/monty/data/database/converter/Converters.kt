package com.monty.data.database.converter

import androidx.room.TypeConverter
import com.monty.tool.extensions.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class Converters {

    @TypeConverter
    fun fromStringDate(value: String?): LocalDate? {
        return value?.toLocalDate()
    }

    @TypeConverter
    fun fromStringDateTime(value: String?): LocalDateTime? {
        return value?.toLocalDateTime()
    }

    @TypeConverter
    fun fromStringTime(value: String?): LocalTime? {
        return value?.toLocalTime()
    }

    @TypeConverter
    fun toStringDate(value: LocalDate?): String? {
        return value?.toStringYMD()
    }

    @TypeConverter
    fun toStringDateTime(value: LocalDateTime?): String? {
        return value?.toISOString()
    }

    @TypeConverter
    fun toStringTime(value: LocalTime?): String? {
        return value?.toHmm()
    }
}
