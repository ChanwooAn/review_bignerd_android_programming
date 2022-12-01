package com.example.photogallery.PhotoGalleryFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.R


private const val TAG="PhotoGalleryFragment"


class PhotoFragment : Fragment() {
    private lateinit var photoRecyclerView: RecyclerView
    private val photoGalleryViewModel by lazy{
        ViewModelProvider(this)[PhotoGalleryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_photo_gallery,container,false)

        photoRecyclerView=view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager=GridLayoutManager(context,3)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner,
            Observer {
                Log.d(TAG,"Have Gallery items from ViewModel $it")
                photoRecyclerView.adapter=PhotoAdapter(it)
            }
        )

    }

    companion object{
        fun newInstance()=PhotoFragment()
    }


}