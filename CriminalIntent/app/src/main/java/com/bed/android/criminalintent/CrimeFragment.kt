package com.bed.android.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bed.android.criminalintent.Model.Crime
import com.bed.android.criminalintent.vm.CrimeDetailViewModel
import java.util.*

private const val ARG_CRIME_ID="crime-id"
private const val TAG="CrimeFragment"
private const val DIALOG_DATE="DialogDate"
private const val REQUEST_DATE="0"



class CrimeFragment : Fragment(){
    private lateinit var crime: Crime
    private lateinit var titleField:EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox:CheckBox
    private val crimeDetailViewModel:CrimeDetailViewModel by lazy{
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        crime= Crime()
        val crimeId=arguments?.getSerializable(ARG_CRIME_ID) as UUID
        Log.d(TAG,"args bundle crime ID: $crimeId")
        crimeDetailViewModel.loadCrime(crimeId)



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_crime,container,false)

        titleField=view.findViewById(R.id.crime_title) as EditText
        dateButton=view.findViewById(R.id.crime_date) as Button
        solvedCheckBox=view.findViewById(R.id.crime_solved) as CheckBox


        solvedCheckBox.apply {
            setOnCheckedChangeListener{
                _,isChecked->
                crime.isSolved=isChecked
            }
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this@CrimeFragment.parentFragmentManager.setFragmentResultListener(REQUEST_DATE,viewLifecycleOwner)
        { key, bundle->
            if(key== REQUEST_DATE){
                crime.date=bundle.get(DIALOG_DATE) as Date
                updateUI()
            }
        }
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { crime->
                crime?.let{
                    this.crime=crime
                    updateUI()
                }
            }
        )

    }
    fun updateUI(){
        titleField.setText(crime.title)
        dateButton.text=crime.date.toString()
        solvedCheckBox.isChecked=crime.isSolved
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher=object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title= p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        titleField.addTextChangedListener(titleWatcher)



        dateButton.setOnClickListener{
            DatePickerFragment.newInstance(crime.date).apply {

                show(this@CrimeFragment.parentFragmentManager, DIALOG_DATE)
            }
        }
    }
    override fun onStop(){
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    companion object{
        fun newInstance(crimeId: UUID):CrimeFragment{
            val args=Bundle().apply {
                putSerializable(ARG_CRIME_ID,crimeId)
            }
            return CrimeFragment().apply {
                arguments=args
            }
        }
    }

}