package com.bed.android.criminalintent.vm

import android.provider.Settings.Global.getString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bed.android.criminalintent.CrimeRepository
import com.bed.android.criminalintent.Model.Crime
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class CrimeDetailViewModel : ViewModel() {
    private val crimeRepository=CrimeRepository.get()
    private val crimeIdLiveData=MutableLiveData<UUID>()
    var crimeLiveData= Transformations.switchMap(crimeIdLiveData){
        crimeId->crimeRepository.getCrime(crimeId)
        //Crime을 반환

    }
    val pie= ArrayList<String>()

    fun getPhotoFile(crime:Crime): File {
        return crimeRepository.getPhotoFile(crime)
    }
    fun loadCrime(crimeId:UUID){
        crimeIdLiveData.value=crimeId
    }

    fun saveCrime(crime: Crime){
        crimeRepository.updateCrime(crime)
    }
}