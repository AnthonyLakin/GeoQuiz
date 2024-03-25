package com.lakin.msu.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.lakin.msu.geoquiz.databinding.ActivityCheatBinding
private const val TAG = "CheatActivity"
private const val EXTRA_ANSWER_STATE = "com.lakin.msu.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCheatBinding

    // declares cheatViewModel as View Model
    private val cheatViewModel:CheatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // This is the data of the parent, which is from the questionBank
        cheatViewModel.answerStateSetter(intent.getBooleanExtra(EXTRA_ANSWER_STATE, false))

        //  This statement test if the Show Answer button was ever clicked
        if (cheatViewModel.buttonClicked) {
            // If button was clicked then call setAnswerShownResult and pass argument

            setAnswerShownResult(true)
        } else {
            // If button was not clicked then call setAnswerShownResult and pass argument

            setAnswerShownResult(false)

        }

        // When the show answer button is clicked it passes boolean from answerState
        // calls setAnswerShownResult and passes true
        // buttonClickSetter is now true
        binding.showAnswerButton.setOnClickListener{
            setAnswerShownResult(true)
            cheatViewModel.buttonClickSetter(true)
        }





    }

    // sets boolean parameter
    private fun setAnswerShownResult(isAnswerShown: Boolean) {

        //cheatViewModel.cheatingSetter(isAnswerShown)

        // Moved button logic to this function
        // to ensure data is persistent no matter if activity is destroyed
        if (isAnswerShown) {
            cheatViewModel.buttonClickSetter(true)
            binding.answerTextView.text = cheatViewModel.answerState.toString()
        }


        // Creates an instance of the class Intent
        val data = Intent().apply {

            // adds this key value pair which the parent can then query
            putExtra(EXTRA_ANSWER_STATE, isAnswerShown)
        }

        // This is for confirmation that the child process has been complete
        setResult(Activity.RESULT_OK, data)

    }

    // This is called when CheatActivity is started. This initiates the Intent class
    companion object {
        fun newIntent(packageContext: Context, fromParent: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_STATE, fromParent)
            }
        }
    }
}