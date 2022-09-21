package com.bed.android.criminalintent.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bed.android.criminalintent.CrimeListFragment
import com.bed.android.criminalintent.Model.Crime
import com.bed.android.criminalintent.R
import java.util.*

private const val TAG="CrimeAdapter"
class CrimeAdapter(var crimes:List<Crime>,var context:Context) : RecyclerView.Adapter<CrimeAdapter.CrimeHolder>(){

    inner class CrimeHolder(private val view: View) :RecyclerView.ViewHolder(view),View.OnClickListener{
        private val titleTextView:TextView=view.findViewById(R.id.crime_title)
        private val dateTextView:TextView=view.findViewById(R.id.crime_date)
        private val solvedImageView:ImageView=view.findViewById(R.id.crime_solved)
        lateinit var crimeId:UUID

        fun bind(crime: Crime){
            titleTextView.text=crime.title
            dateTextView.text=crime.date.toString()
            crimeId=crime.id
            Log.d(TAG,crime.date.toString())
            solvedImageView.visibility=if(crime.isSolved){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }

            view.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            (context as CrimeListFragment.Callbacks).onCrimeSelected(crimeId)
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