package com.bed.android.criminalintent.Model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
val dateformat=SimpleDateFormat("EEE, MMM dd yyyy, kk : mm aaa ")

data class Crime(
    val id:UUID=UUID.randomUUID(),
    var title:String="",
    var date: String = dateformat.format(Date()),
    var isSolved:Boolean = false
)