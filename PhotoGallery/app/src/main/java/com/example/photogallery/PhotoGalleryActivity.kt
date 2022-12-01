package com.example.photogallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photogallery.PhotoGalleryFragment.PhotoFragment

class PhotoGalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)
        
        val isFragmentContainerEmpty= (savedInstanceState==null)
        if(isFragmentContainerEmpty){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PhotoFragment.newInstance())
                .commit()
        }
        //isFragmentContainerEmpty 가 false일 경우 이미 호스팅 된 fragment가 있다는 의미.
        //즉 화면전환 등 상태변경으로 인해서 fragment가 예전에 이미 호스팅 되었다는것을 의미한다.


        
        
    }
}