package com.lakin.msu.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.lakin.msu.geoquiz.databinding.ActivityMainBinding
import java.util.Arrays

private const val TAG = "MainActivity"
const val EXTRA_ANSWER_SHOWN = "com.lakin.msu.geoquiz.answer_shown"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel:QuizViewModel by viewModels()

    // In android Activity class get the results of the 2nd activity
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )

    // This is a lambda that is invoked when the 2nd activity is done and returns a result
    {
        // If conditional statement is true call cheatingHandler within the quizViewModel class
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val answerShown = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
            Log.v(TAG, "Received answerShown: $answerShown")
            quizViewModel.cheatingHandler(answerShown ?: false)
            quizViewModel.cheatHistoryHandler(quizViewModel.getCurrentIndex, answerShown ?: false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // When cheatButton is pressed it creates the 2nd activity
        binding.cheatButton.setOnClickListener{

            // This is the value of the current question bank answer
            val currentQuestionAnswer = quizViewModel.getQuestionBank[quizViewModel.getCurrentIndex].answer

            // instantiates Intent object and passes the current question Boolean value
            val intent = CheatActivity.newIntent(this@MainActivity, currentQuestionAnswer)

            // starts and launches Intent object
            cheatLauncher.launch(intent)
        }
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        updateQuestion()

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }
        binding.questionTextview.setOnClickListener {
            questionHandler(true)
        }
        binding.nextButton.setOnClickListener {
            questionHandler(true)
        }
        binding.prevButton.setOnClickListener {
            questionHandler(false)
        }
        binding.resetButton.setOnClickListener {
            resetAll(true)
        }


    }

    private fun questionHandler(typeBool: Boolean) {
        var currentIndex = quizViewModel.getCurrentIndex
        val questionBank = quizViewModel.getQuestionBank


        if (typeBool) {
            if (currentIndex != (questionBank.size - 1)) {
                quizViewModel.currentIndexHandler("next")
            } else {
                quizViewModel.currentIndexHandler("max")
            }
        } else  {
            if (currentIndex != 0) {
                quizViewModel.currentIndexHandler("back")
            } else {
                quizViewModel.currentIndexHandler("min")
            }
        }

        if (quizViewModel.getCheatHistory[quizViewModel.getCurrentIndex]) {
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show()
        }

        updateQuestion()
        currentIndex = quizViewModel.getCurrentIndex
        val history: MutableSet<Int> = quizViewModel.getHistory
        if (currentIndex in history) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
            binding.cheatButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            binding.cheatButton.isEnabled = true
        }

    }

    private fun updateQuestion() {
        val currentIndex = quizViewModel.getCurrentIndex
        val questionBank = quizViewModel.getQuestionBank
        val history = quizViewModel.getHistory



        if (currentIndex in history) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
            binding.cheatButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            binding.cheatButton.isEnabled = true
        }
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)
    }


    private fun checkAnswer(userAnswer: Boolean) {
        val currentIndex = quizViewModel.getCurrentIndex
        val questionBank = quizViewModel.getQuestionBank

        quizViewModel.addToHistory(currentIndex)
            val correctAnswer = questionBank[currentIndex].answer
            /*val messageResId = if (userAnswer == correctAnswer) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }*/

        // Replaced commented code with this
        // This code uses a switch statement to check the boolean value if the user cheated
        val messageResId = when {
            quizViewModel.getIsCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        quizViewModel.percentHandler("bottom")
            //questionHandler(true)
            updateQuestion()
            if (messageResId == R.string.correct_toast) {
                quizViewModel.percentHandler("top")
            }

        val denominator: Int = quizViewModel.getDenominator
        val numerator: Int = quizViewModel.getNumerator
        if (denominator == questionBank.size) {
            val result = (numerator.toDouble() / denominator) * 100
            val formattedResult = String.format("%.1f%%", result)
            Toast.makeText(this, formattedResult, Toast.LENGTH_SHORT).show()
            Log.d(TAG, formattedResult)
            resetAll(false)
        }


    }



    private fun resetAll(currentState: Boolean) {
        if (currentState) {
            binding.resetButton.visibility = View.INVISIBLE
            binding.resetButton.isEnabled = false
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            binding.cheatButton.isEnabled = true
            quizViewModel.resetVars()
            updateQuestion()
        } else {
            binding.resetButton.visibility = View.VISIBLE
            binding.resetButton.isEnabled = true
        }


    }

}
