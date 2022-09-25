package com.bed.android.criminalintent.vm

import androidx.lifecycle.ViewModel
import com.bed.android.criminalintent.CrimeRepository
import com.bed.android.criminalintent.Model.Crime

class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

    fun addCrime(crime: Crime){
        crimeRepository.addCrime(crime)
    }

}