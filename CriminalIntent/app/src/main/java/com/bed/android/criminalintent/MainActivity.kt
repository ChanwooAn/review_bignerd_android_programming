package com.bed.android.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {



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



}