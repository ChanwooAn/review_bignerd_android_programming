package com.example.photogallery.api

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PhotoDeserializer: JsonDeserializer<PhotoResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {

        val jsonObject =
            json?.asJsonObject ?: throw NullPointerException("Response Json String is null")
        val photoArr= jsonObject.get("photos").asJsonObject.get("photo").asJsonArray

        Log.d("PhotoDeserializer",photoArr.toString())

        val listOfGallery = photoArr.map {
            val photo = it.asJsonObject
            Log.d("PhotoDeserializer",photo["url_s"].asString)

            GalleryItem(photo["title"].asString, photo["id"].asString, photo["url_s"].asString)
        }

        return PhotoResponse().apply { galleryItem =listOfGallery}




    }
}