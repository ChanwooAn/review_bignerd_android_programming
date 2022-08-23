package com.bed.android.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bed.android.criminalintent.Model.Crime


@Database(entities = [Crime::class],version=1)
@TypeConverters(CrimeTypeConverter::class)

abstract class CrimeDatabase :RoomDatabase(){

    abstract fun crimeDao(): CrimeDao
    //이렇게 추상 클래스로 선언해두면 Room이 CrimeDao 구현 클래스를 생성한다.
}