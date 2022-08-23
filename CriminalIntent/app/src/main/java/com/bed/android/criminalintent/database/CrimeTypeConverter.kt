package com.bed.android.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverter {
    @TypeConverter
    fun fromDate(date: Date?):Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(date:Long?):Date?{
        return date?.let{
            Date(it)
        }
    }

    @TypeConverter
    fun fromUUID(uuid:UUID?):String?{
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid:String?):UUID?{
        return UUID.fromString(uuid)
    }




}