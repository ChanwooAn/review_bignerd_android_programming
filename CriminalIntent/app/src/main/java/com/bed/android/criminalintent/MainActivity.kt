package com.bed.android.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class MainActivity : AppCompatActivity(),CrimeListFragment.Callbacks {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val currentFragment=supportFragmentManager.findFragmentById(R.id.main_frame)

        if(currentFragment==null){
            val fragment=CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_frame,fragment)
                .commit()
        }

    }

    override fun onCrimeSelected(crimeId: UUID) {
        val fragment=CrimeFragment.newInstance(crimeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame,fragment)
            .addToBackStack(null)
            .commit()

    }



}