package com.example.photogallery.PhotoGalleryFragment

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.api.GalleryItem

class PhotoAdapter(private val galleryItem: List<GalleryItem>):RecyclerView.Adapter<PhotoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val textView=TextView(parent.context)
        return PhotoHolder(textView)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem=galleryItem[position]
        holder.bindTitle(galleryItem.title)
    }

    override fun getItemCount(): Int {
        return galleryItem.size
    }


}