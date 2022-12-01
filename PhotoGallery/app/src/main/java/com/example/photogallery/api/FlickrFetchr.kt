package com.example.photogallery.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG="FlickrFetchr"
class FlickrFetchr {
    private val flickrApi:FlickrApi

    init{
        val gsonBuilder=GsonBuilder().registerTypeAdapter(PhotoResponse::class.java,PhotoDeserializer())
        val retrofit:Retrofit=Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .build()

        flickrApi=retrofit.create(FlickrApi::class.java)

    }

    fun fetchPhotos():LiveData<List<GalleryItem>>{
        val responseLiveData:MutableLiveData<List<GalleryItem>> = MutableLiveData()
        val flickrRequest: Call<PhotoResponse> =flickrApi.fetchPhotos()

        flickrRequest.enqueue(object : Callback<PhotoResponse>{
            override fun onResponse(call: Call<PhotoResponse>, response: Response<PhotoResponse>) {
                Log.d(TAG,"Response received")
                val photoResponse=response.body()
                var galleryItem=photoResponse?.galleryItem?.filterNot{it.url.isBlank()}
                responseLiveData.value=galleryItem
            }

            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.d(TAG,"Failed to fetch photos",t)
            }

        })
        return responseLiveData
    }

}