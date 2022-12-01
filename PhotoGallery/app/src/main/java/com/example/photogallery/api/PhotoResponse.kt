package com.example.photogallery.api

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItem: List<GalleryItem>
}