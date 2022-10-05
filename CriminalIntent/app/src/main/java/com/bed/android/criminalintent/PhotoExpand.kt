package com.bed.android.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_BITMAP="bitmap"
class PhotoExpand : DialogFragment() {
    lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bitmap=arguments?.get(ARG_BITMAP) as Bitmap
        Log.d("photoDialog",bitmap.toString())
       // isCancelable=true
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.dialog_photo_expand,container,false)
        imageView=view.findViewById(R.id.photo_expand_imageView)

        bitmap.let {
            imageView.setImageBitmap(bitmap)
            Log.d("photoDialog",bitmap.toString())
        }


        return super.onCreateView(inflater, container, savedInstanceState)

    }


    companion object{
        fun newInstance(bitmap: Bitmap):DialogFragment{
            val args= Bundle().apply {
                putParcelable(ARG_BITMAP,bitmap)
            }

            return DialogFragment().apply {
                arguments=args
            }
        }
    }



}