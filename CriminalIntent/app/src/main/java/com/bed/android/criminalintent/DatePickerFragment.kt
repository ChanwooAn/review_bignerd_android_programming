package com.bed.android.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val REQUEST_DATE="0"
private const val ARG_DATE="date"
private const val DIALOG_DATE="DialogDate"


class DatePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date=arguments?.get(ARG_DATE) as Date

        val calendar= Calendar.getInstance()
        calendar.time=date//CrimeFragment에서 전달받은 date로 Datepicker를 설정.

        val initialYear= calendar.get(Calendar.YEAR)
        val initialMonth=calendar.get(Calendar.MONTH)
        val initialDay=calendar.get(Calendar.DAY_OF_MONTH)


        val dateListener= DatePickerDialog.OnDateSetListener{
                _: DatePicker, year:Int, month:Int, day:Int->

            val resultDate:Date=GregorianCalendar(year,month,day).time

            parentFragmentManager.setFragmentResult(REQUEST_DATE,Bundle().apply {
                putSerializable(DIALOG_DATE,resultDate) })

        }



        return DatePickerDialog(
            requireContext(),
            dateListener, // 날짜 선택한 것에 대한 listener
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object{
        fun newInstance(date:Date):DatePickerFragment{
            val args=Bundle().apply {
                putSerializable(ARG_DATE,date)
            }

            return DatePickerFragment().apply {
                arguments=args
            }
        }
    }


}