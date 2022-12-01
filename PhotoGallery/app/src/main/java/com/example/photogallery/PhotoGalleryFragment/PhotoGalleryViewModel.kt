package com.example.photogallery.PhotoGalleryFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery.api.FlickrFetchr
import com.example.photogallery.api.GalleryItem

class PhotoGalleryViewModel: ViewModel() {
    val galleryItemLiveData:LiveData<List<GalleryItem>>

    init{
        galleryItemLiveData= FlickrFetchr().fetchPhotos()
    }

}