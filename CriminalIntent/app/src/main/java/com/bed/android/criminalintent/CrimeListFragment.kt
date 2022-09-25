package com.bed.android.criminalintent

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
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
    private lateinit var textNoItem: TextView
    private lateinit var adapter:CrimeAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_crime->{
                val crime=Crime()
                crimeListViewModel.addCrime(crime)
                callbacks?.onCrimeSelected(crime.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_crime_list,container,false)
        crimeList= view.findViewById(R.id.crime_list_recycler)
        textNoItem=view.findViewById(R.id.crime_list_textView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"onViewCreated")
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

        if(crimes.size== 0){
            Log.d(TAG,"1")
            crimeList.visibility=View.INVISIBLE
            textNoItem.visibility=View.VISIBLE

        }else{
            Log.d(TAG,"2")

            crimeList.visibility=View.VISIBLE
            textNoItem.visibility=View.INVISIBLE
        }
    }
    
    
    companion object{
        fun newInstance():CrimeListFragment {
            return CrimeListFragment()
        }
    }

}