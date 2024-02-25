package com.lakin.msu.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.lakin.msu.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel:QuizViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        //Retrieved values from QuizViewModel
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

        updateQuestion()
        currentIndex = quizViewModel.getCurrentIndex
        val history: MutableSet<Int> = quizViewModel.getHistory
        if (currentIndex in history) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

    }

    private fun updateQuestion() {
        //Retrieved values from QuizViewModel
        val currentIndex = quizViewModel.getCurrentIndex
        val questionBank = quizViewModel.getQuestionBank
        val history = quizViewModel.getHistory

        if (currentIndex in history) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)
    }


    private fun checkAnswer(userAnswer: Boolean) {
        //Retrieved values from QuizViewModel
        val currentIndex = quizViewModel.getCurrentIndex
        val questionBank = quizViewModel.getQuestionBank

        quizViewModel.addToHistory(currentIndex)
            val correctAnswer = questionBank[currentIndex].answer
            val messageResId = if (userAnswer == correctAnswer) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        quizViewModel.percentHandler("bottom")
            questionHandler(true)
            if (messageResId == R.string.correct_toast) {
                quizViewModel.percentHandler("top")
            }

        //Declared fraction components. retrieved value from QuizViewModel
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
            quizViewModel.resetVars()
            updateQuestion()
        } else {
            binding.resetButton.visibility = View.VISIBLE
            binding.resetButton.isEnabled = true
        }


    }

}
