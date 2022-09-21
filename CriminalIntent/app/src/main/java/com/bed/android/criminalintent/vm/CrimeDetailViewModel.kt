package com.bed.android.criminalintent.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bed.android.criminalintent.CrimeRepository
import com.bed.android.criminalintent.Model.Crime
import java.util.*

class CrimeDetailViewModel : ViewModel() {
    private val crimeRepository=CrimeRepository.get()
    private val crimeIdLiveData=MutableLiveData<UUID>()

    var crimeLiveData= Transformations.switchMap(crimeIdLiveData){
        crimeId->crimeRepository.getCrime(crimeId)
        //Crime을 반환

    }


    fun loadCrime(crimeId:UUID){
        crimeIdLiveData.value=crimeId
    }

    fun saveCrime(crime: Crime){
        crimeRepository.updateCrime(crime)
    }
}