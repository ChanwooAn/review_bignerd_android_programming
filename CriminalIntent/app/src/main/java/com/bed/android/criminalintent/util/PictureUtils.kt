package com.bed.android.criminalintent.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import androidx.activity.result.contract.ActivityResultContracts

class PictureUtils {

}


fun getScaledBitmap(path:String,activity:Activity):Bitmap{
    val size= Point()

    @Suppress("DEPRECATION")
    activity.windowManager.defaultDisplay.getSize(size)
    //activity의 화면 크기를 확인한다.

    return getScaledBitmap(path,size.x,size.y)

}

fun getScaledBitmap(path:String,destWidth:Int,destHeight:Int): Bitmap {
    var options=BitmapFactory.Options()// bitmap을 decoding할 때 사용하는 option들
    options.inJustDecodeBounds=true // bitmap을 메모리에 할당하지 않고, outWidth/outHeight/outMimeType이 설정된다.
    // 따라서 메모리를 사용하지 않으면서 크기를 구할 수 있게된다. 대신 bitmap 객체에는 null이 할당됨.

    BitmapFactory.decodeFile(path,options)
    //decode 했지만 inJustDecodeBounds에 의해서 Bitmap 객체는 반환되지 않는다. 하지만 options에 outWidth와 outHeight가 설정된다.

    val srcWidth=options.outWidth.toFloat()
    val srcHeight=options.outHeight.toFloat()

    var inSampleSize=1
    if(srcHeight>destHeight ||srcWidth>destWidth){
        val heightScale=srcHeight/destHeight
        val widthScale=srcWidth/destWidth

        val sampleScale=if(heightScale>widthScale){
            heightScale
        }else{
            widthScale
        }
        inSampleSize=Math.round(sampleScale)//반올림
    }

    options=BitmapFactory.Options()
    options.inSampleSize=inSampleSize
    /*
        inSampleSize가 1이면 본래 화소 1당 1화소. 2면 본래 화소 2당 1화소로 줄인다.
        즉 만약 inSampleSize가 2라면 가로가 반으로 줄고, 세로도 반으로 줄게됨으로써 본래 부피의 4/1이 된다.
     */

    return BitmapFactory.decodeFile(path,options)
}


/*
클래스 외부에 정의된 함수를 파일 수준함수라고 하며, 앱의 어떤 코드에서도 이용 가능하다.

 */