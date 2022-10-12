package com.bed.android.criminalintent

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.ImageView
import androidx.appcompat.widget.AlertDialogLayout
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

    @SuppressLint("UseCompatLoadingForDrawables")
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


        return view

    }

    override fun onResume() {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30){
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val window = dialog?.window
            val x = (size.x*0.7 ).toInt()
            val y = (size.y*0.6 ).toInt()
            window?.setLayout(x, y)

        }else{
            val rect = windowManager.currentWindowMetrics.bounds
            val window = dialog?.window
            val x = (rect.width()*0.5).toInt()
            val y = (rect.height()*0.5 ).toInt()
            window?.setLayout(x, y)
        }

        super.onResume()
    }


    companion object{
        fun newInstance(bitmap: Bitmap):DialogFragment{
            val args= Bundle().apply {
                putParcelable(ARG_BITMAP,bitmap)
            }

            return PhotoExpand().apply {
                arguments=args
            }
        }
    }



}