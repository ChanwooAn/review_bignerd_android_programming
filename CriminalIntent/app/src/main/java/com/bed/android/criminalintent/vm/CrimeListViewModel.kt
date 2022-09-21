package com.bed.android.criminalintent.vm

import androidx.lifecycle.ViewModel
import com.bed.android.criminalintent.CrimeRepository

class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

}