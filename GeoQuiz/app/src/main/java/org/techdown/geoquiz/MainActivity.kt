package org.techdown.geoquiz

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import org.techdown.geoquiz.CheatActivity.Companion.EXTRA_ANSWER_SHOWN


private const val KEY_INDEX="index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton:Button
    private lateinit var nextButton :Button
    private lateinit var previousButton:Button
    private lateinit var questionTextView:TextView
    private lateinit var cheatButton:Button
    val quizViewModel by lazy{
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX,quizViewModel.currentString)
        Log.d("MainActivity","onSave"+quizViewModel.currentString)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentString=savedInstanceState?.getInt(KEY_INDEX,0)?:0
        quizViewModel.currentString=currentString
        Log.d("MainActivity",quizViewModel.tmp.toString())
        trueButton=findViewById(R.id.true_btn)
        falseButton=findViewById(R.id.false_btn)
        nextButton=findViewById(R.id.next_btn)
        previousButton=findViewById(R.id.previous_btn)
        questionTextView=findViewById(R.id.question_text)
        cheatButton=findViewById(R.id.cheat_button)


        trueButton.setOnClickListener{view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener{view: View ->
            checkAnswer(false)

        }
        nextButton.setOnClickListener{view:View->

            quizViewModel.nextBtn()
            updateQuestion()

        }
        previousButton.setOnClickListener{



            updateQuestion()

        }
        val cheatResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            when(result.resultCode){
                RESULT_OK->{
                    val answerShown= result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false)
                    quizViewModel.isCheat=answerShown?:false
                }
            }
        }
        cheatButton.setOnClickListener{


            CheatActivity.newIntent(this,quizViewModel.currentAnswer).also { cheatResult.launch(it) }
        }
        updateQuestion()


    }

    private fun checkAnswer(answer:Boolean){
       // arrSolved[currentIndex]=true
        //sumOfSolve++
        if(quizViewModel.currentAnswer==answer){
            if(quizViewModel.isCheat){
                Toast.makeText(this,R.string.judgement_toast,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,R.string.correct_toast,Toast.LENGTH_SHORT).show()

            }


        }else{
            Toast.makeText(this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show()
        }



    }
    private fun updateQuestion(){
        questionTextView.setText(getString(quizViewModel.currentString))
    }



}