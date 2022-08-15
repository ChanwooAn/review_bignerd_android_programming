package org.techdown.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG="QuizViewModel"
class QuizViewModel : ViewModel() {
    init{
        Log.d(TAG,"ViewModel Instance created")

    }
    private val questionBank=listOf(
        Question(R.string.question_0,true),
        Question(R.string.question_1,true),
        Question(R.string.question_2,false),
        Question(R.string.question_3,false),
        Question(R.string.question_4,true),
        Question(R.string.question_5,true)
    )
    var tmp=0

    private var currentIndex=0
    val currentAnswer : Boolean
        get() = questionBank[currentIndex].answer
    var currentString : Int=1
        get()= questionBank[currentIndex].textResId

    var isCheat=false

    private var arrSolved= Array<Boolean>(questionBank.size){false}
    private val numOfTotalQuestion=questionBank.size
    private var sumOfTrue=0
    private var sumOfSolve=0

    fun nextBtn(){
        currentIndex=(currentIndex+1)%questionBank.size

    }


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"ViewModel Cleared")
    }


}