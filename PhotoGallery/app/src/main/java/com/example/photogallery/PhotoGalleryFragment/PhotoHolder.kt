package com.example.photogallery.PhotoGalleryFragment

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhotoHolder(itemTextView: TextView) : RecyclerView.ViewHolder(itemTextView) {

    val bindTitle:(CharSequence)->Unit = itemTextView::setText


}