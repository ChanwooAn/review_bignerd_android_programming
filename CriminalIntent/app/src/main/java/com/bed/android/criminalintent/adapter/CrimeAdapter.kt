package com.bed.android.criminalintent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bed.android.criminalintent.Model.Crime
import com.bed.android.criminalintent.R

class CrimeAdapter(var crimes:List<Crime>,var context:Context) : RecyclerView.Adapter<CrimeAdapter.CrimeHolder>(){

    inner class CrimeHolder(view: View) :RecyclerView.ViewHolder(view){
        val titleTextView:TextView=view.findViewById(R.id.crime_title)
        val dateTextView:TextView=view.findViewById(R.id.crime_date)

        fun bind(crime: Crime){
            titleTextView.text=crime.title
            dateTextView.text=crime.date.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.list_item_crime,parent,false)


        return CrimeHolder(view)

    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime=crimes[position]

        holder.bind(crime)

    }

    override fun getItemCount(): Int {
        return crimes.size

    }


}