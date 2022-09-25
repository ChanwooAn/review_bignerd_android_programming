package com.bed.android.criminalintent.Model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
val dateFormat=SimpleDateFormat("EEE, MMM dd yyyy, kk : mm aaa ")

@Entity
data class Crime(
    @PrimaryKey val id:UUID=UUID.randomUUID(),
    var title:String="",
    var date: Date = Date(),
    var isSolved:Boolean = false,
    var suspect:String=""
)