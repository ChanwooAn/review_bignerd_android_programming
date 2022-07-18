package org.techdown.geoquiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton:Button
    private lateinit var nextButton :Button
    private lateinit var previousButton:Button
    private lateinit var questionTextView:TextView

    private val questionBank=listOf(
        Question(R.string.question_0,true),
        Question(R.string.question_1,true),
        Question(R.string.question_2,false),
        Question(R.string.question_3,false),
        Question(R.string.question_4,true),
        Question(R.string.question_5,true)
    )

    private var currentIndex=0
    private var arrSolved= Array<Boolean>(questionBank.size){false}
    private val numOfTotalQuestion=questionBank.size
    private var sumOfTrue=0
    private var sumOfSolve=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton=findViewById(R.id.true_btn)
        falseButton=findViewById(R.id.false_btn)
        nextButton=findViewById(R.id.next_btn)
        previousButton=findViewById(R.id.previous_btn)
        questionTextView=findViewById(R.id.question_text)

        trueButton.setOnClickListener{view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener{view: View ->
            checkAnswer(false)

        }
        nextButton.setOnClickListener{view:View->
            while(arrSolved[currentIndex]){
                currentIndex= (currentIndex+1)%questionBank.size
            }

            updateQuestion()

        }
        previousButton.setOnClickListener{
            while(!arrSolved[currentIndex]){
                if(currentIndex==0){
                    currentIndex=questionBank.size-1
                }else{
                    currentIndex--
                }
            }

            updateQuestion()

        }

        updateQuestion()


    }

    private fun checkAnswer(answer:Boolean){
        arrSolved[currentIndex]=true
        sumOfSolve++
        if(questionBank[currentIndex].answer==answer){
            Toast.makeText(this,R.string.correct_toast,Toast.LENGTH_SHORT).show()
            sumOfTrue++

        }else{
            Toast.makeText(this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show()
        }
        if(sumOfSolve==numOfTotalQuestion){
            previousButton.isEnabled = false
            nextButton.isEnabled = false
            var score=(sumOfTrue.toFloat()/numOfTotalQuestion.toFloat())*100
            Toast.makeText(this,"Score : "+score,Toast.LENGTH_SHORT).show()

        }

    }
    private fun updateQuestion(){
        questionTextView.setText(getString(questionBank[currentIndex].textResId))
    }
}