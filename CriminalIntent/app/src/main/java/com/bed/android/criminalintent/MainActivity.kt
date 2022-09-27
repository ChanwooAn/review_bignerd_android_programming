package com.bed.android.criminalintent

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity(),CrimeListFragment.Callbacks {


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Log.d("Permission","granted")
        }else{
            Log.d("Permission","denied")
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status= ContextCompat.checkSelfPermission(this,"android.permission.READ_CONTACTS")
        if(status ==PackageManager.PERMISSION_GRANTED){
            Log.d("Permission","permission granted")
        }else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.READ_CONTACTS"),
                100
            )

        }

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