package com.example.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.Exception

private const val TAG="BeatBox"
private const val SOUNDS_FOLDER="sample_sounds"
private const val MAX_SOUNDS=5


class BeatBox(private val assets: AssetManager) {
    val sounds:List<Sound>
    private val soundPool= SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    init{
        sounds=loadsSound()
    }



    private fun loadsSound():List<Sound>{
        val soundNames:Array<String>

        try{
            soundNames=assets.list(SOUNDS_FOLDER)!!

        }catch (e:Exception){
            Log.e(TAG,"Could not list assets",e)
            return emptyList()
        }
        val sounds= mutableListOf<Sound>()
        soundNames.forEach {
            fileName->
            val assetPath="$SOUNDS_FOLDER/$fileName"
            val sound=Sound(assetPath)

            try{
                load(sound)
                sounds.add(sound)
            }catch(ioe:IOException){
                Log.e(TAG,"Could not load $fileName",ioe)
            }//sound를 load할 때 openFd를 수행한다. 이 과정에서 IoException이 발생할 수 있으므로 try-catch로 처리해줘야 안전하다.


        }
        return sounds
    }
    private fun load(sound:Sound){
        val afd: AssetFileDescriptor =assets.openFd(sound.assetPath)
        val soundId=soundPool.load(afd,1)
        sound.soundId=soundId


    }


    fun play(sound:Sound){
        sound.soundId?.let{
            soundPool.play(it,1.0f,1.0f,1,0,1.0f)
            //음원 id, 왼쪽볼륨, 오른쪽 볼륨, stream 우선순위(0이 최대), 반복재생 여부, 재생속도
        }
    }
    fun release(){
        soundPool.release()
    }


}