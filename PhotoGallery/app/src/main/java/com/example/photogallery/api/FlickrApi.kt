package com.example.photogallery.api

import com.example.photogallery.BuildConfig
import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {


    @GET("/")
    fun fetchContents(): Call<String>
    // /는 상대경로를 의미한다.  /는 전달된 기본 api경로를 이용한다.

    @GET(
        "services/rest/?method=flickr.interestingness.getList"+
                "&api_key=${BuildConfig.flickrApiKey}"+
                "&format=json"+
                "&nojsoncallback=1"+
                "&extras=url_s"
    )
    fun fetchPhotos():Call<PhotoResponse>
}