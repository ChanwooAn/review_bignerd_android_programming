package org.techdown.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class CheatActivity : AppCompatActivity() {


    lateinit var answerTextView:TextView
    lateinit var showAnswerButton:Button




    private var answer=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answer=intent.getBooleanExtra(INTENT_EXTRA_ANSWER,false)

        answerTextView=findViewById(R.id.answer_text_view)
        showAnswerButton=findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener{
            val answerText= when(answer){
                true-> R.string.true_button
                false->R.string.false_button
            }
            answerTextView.setText(answerText)
            setResult(Activity.RESULT_OK,Intent().putExtra(EXTRA_ANSWER_SHOWN,true))
        }




    }



    companion object{
        private const val INTENT_EXTRA_ANSWER="org.techdown.geoquiz"
        const val EXTRA_ANSWER_SHOWN="org.techdown.geoquiz.answer_shown"

        fun newIntent(context:Context, answer:Boolean): Intent {
            return Intent(context,CheatActivity::class.java).apply {
                putExtra(INTENT_EXTRA_ANSWER,answer)
            }
        }

    }
}