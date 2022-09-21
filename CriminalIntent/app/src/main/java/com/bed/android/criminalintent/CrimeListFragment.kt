package com.bed.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bed.android.criminalintent.Model.Crime
import com.bed.android.criminalintent.vm.CrimeListViewModel
import com.bed.android.criminalintent.adapter.CrimeAdapter
import java.util.*

private const val TAG="CrimeListFragment"

class CrimeListFragment : Fragment() {

    interface Callbacks{
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks:Callbacks?=null




    private val crimeListViewModel: CrimeListViewModel by lazy{
        ViewModelProvider(this@CrimeListFragment)[CrimeListViewModel::class.java]
    }
    private lateinit var crimeList:RecyclerView
    private lateinit var adapter:CrimeAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as Callbacks
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_crime_list,container,false)
        crimeList= view.findViewById(R.id.crime_list_recycler)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        crimeList.layoutManager= LinearLayoutManager(context)
        updateUI(emptyList())

        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner){ crimes-> crimes.let{
            updateUI(crimes)
        }}

    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }

    fun updateUI(crimes:List<Crime>){
        adapter= CrimeAdapter(crimes,callbacks as Context)
        crimeList.adapter=adapter
    }
    
    
    companion object{
        fun newInstance():CrimeListFragment {
            return CrimeListFragment()
        }
    }

}