package com.example.beatbox

import android.app.LauncherActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox:BeatBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox=BeatBox(assets)

        val binding:ActivityMainBinding
        = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.recyclerMain.apply{
            layoutManager=GridLayoutManager(context,3)
            adapter=SoundAdapter(beatBox.sounds)
        }

    }
    private inner class SoundHolder(private val binding :ListItemSoundBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.viewModel= SoundViewModel(beatBox)
        }
        fun bind(sound:Sound){
            binding.apply {
                viewModel?.sound=sound
                executePendingBindings()//recycler view가 즉각 반영하도록 하기 위함
            }
        }

    }
    private inner class SoundAdapter(private val sounds:List<Sound>):RecyclerView.Adapter<SoundHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding=DataBindingUtil.inflate<ListItemSoundBinding>(layoutInflater,
            R.layout.list_item_sound,
            parent,
            false)


            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound=sounds[position]
            holder.bind(sound)
            Log.d("MainActivity","onBind $position ${sound.name}")
            //onBindViewHolder에서는 ViewModel의 data만 변경하고 xml에 직접적으로 접근하지 않는다.
        }



        override fun getItemCount(): Int {
            return sounds.size
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }
}