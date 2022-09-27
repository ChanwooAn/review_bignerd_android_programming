package com.bed.android.criminalintent

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
private const val REQUEST_CONTACT=1
private const val DATE_FORMAT="yyyy년 M월 d일 H시 m분, E요일"


class CrimeFragment : Fragment(){
    private lateinit var crime: Crime
    private lateinit var titleField:EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox:CheckBox
    private lateinit var reportButton:Button
    private lateinit var suspectButton:Button
    private lateinit var callButton: Button

    private lateinit var suspectResult:ActivityResultLauncher<Intent>

    private val crimeDetailViewModel:CrimeDetailViewModel by lazy{
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)



        crime= Crime()
        val crimeId=arguments?.getSerializable(ARG_CRIME_ID) as UUID
        Log.d(TAG,"args bundle crime ID: $crimeId")
        crimeDetailViewModel.loadCrime(crimeId)


        suspectResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            when{

                result.resultCode==RESULT_OK && result.data!=null->{
                    val contactUri: Uri = result.data!!.data?: return@registerForActivityResult
                    Log.d("ContactQuery",contactUri.toString())
                    val cursor = requireActivity().contentResolver.query(
                        contactUri,
                        arrayOf<String>(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,)
                        ,null,null,null
                    )

                    if(cursor?.moveToFirst() == true){
                        //cursor를 first row(행)로 옮긴다. 만약 moveToFirst가 false를 return한다면 cursor가 빈것을 의미한다.
                        val name=cursor.getString(0)
                        val phone=cursor.getString(1)

                        Log.d(TAG,"$name $phone")
                        crime.suspect=name
                        crimeDetailViewModel.saveCrime(crime)
                        suspectButton.text=name
                        callButton.text="Suspect Number: $phone"
                    }





                }
            }
        }


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
        reportButton=view.findViewById(R.id.crime_report) as Button
        suspectButton=view.findViewById(R.id.crime_suspect) as Button
        callButton=view.findViewById(R.id.crime_call) as Button
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
    fun getCrimeReport():String{
        val solvedString= if(crime.isSolved){
            getString(R.string.crime_report_solved)
        }else{
            getString(R.string.crime_report_unsolved)
        }

        val dateString= DateFormat.format(DATE_FORMAT,crime.date).toString()
        var suspect=if(crime.suspect.isBlank()){
            getString(R.string.crime_report_no_suspect)
        }else{
            getString(R.string.crime_report_suspect,crime.suspect)
        }

        return getString(R.string.crime_report,crime.title,dateString,solvedString,suspect)
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
        reportButton.setOnClickListener{
            Intent(Intent.ACTION_SEND).apply {
                type="text/plain"
                putExtra(Intent.EXTRA_TEXT,getCrimeReport()) // 보낼 text생성
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "hihiehiehieihiehi"//제목
                )
            }.also{
                intent->
                val chooserIntent=Intent.createChooser(intent,getString(R.string.send_report))
                startActivity(chooserIntent)

            }
        }

        suspectButton.apply{
            val pickerIntent=Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            setOnClickListener{
                suspectResult.launch(pickerIntent)
            }

            /*val packageManager=requireActivity().packageManager
            val resolveActivity=pickerIntent.resolveActivity(packageManager)
            if(resolveActivity==null) {
                isEnabled = false
            }*/
        }
        callButton.apply {
            val callIntent=Intent(Intent.ACTION_DIAL)



            setOnClickListener {
                val getNumber=callButton.text.replace("[^0-9]".toRegex(),"")
                val number= Uri.parse("tel:${getNumber}")
                Log.d(TAG,number.toString())
                callIntent.setData(number)
                startActivity(callIntent)
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