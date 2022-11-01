package com.example.beatbox

private const val WAV=".wav"
//sound 파일들의 이름 형식을 지정할 클래스이다.

class Sound(val assetPath:String,var soundId:Int?=null) {
    //soundPool에 음원을 load할 때 각 음원은 고유한 id를 가진다.(정수 값)

    val name=assetPath.split("/").last().removeSuffix(WAV)

}