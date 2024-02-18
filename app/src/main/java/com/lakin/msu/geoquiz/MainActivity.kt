package com.lakin.msu.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lakin.msu.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var history = mutableSetOf<Int>()
    private var numerator = 0
    private var denominator = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate (Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

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
        if (typeBool) {
            if (currentIndex != (questionBank.size - 1)) {
                currentIndex++
            } else {
                currentIndex = (questionBank.size - 1)
            }
        } else  {
            if (currentIndex != 0) {
                currentIndex--
            } else {
                currentIndex = 0
            }
        }
        updateQuestion()
        if (currentIndex in history) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

    }

    private fun checkAnswer(userAnswer: Boolean) {
        history.add(currentIndex)
            val correctAnswer = questionBank[currentIndex].answer
            val messageResId = if (userAnswer == correctAnswer) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
            denominator++
            questionHandler(true)
            if (messageResId == R.string.correct_toast) {
                numerator++
            }
        if (denominator == questionBank.size) {
            val result = (numerator.toDouble() / denominator) * 100
            val formattedResult = String.format("%.1f%%", result)
            Toast.makeText(this, formattedResult, Toast.LENGTH_SHORT).show()
            Log.d(TAG, formattedResult)
            resetAll(false)
        }


    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)
    }

    private fun resetAll(currentState: Boolean) {
        if (currentState) {
            binding.resetButton.visibility = View.INVISIBLE
            binding.resetButton.isEnabled = false
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            currentIndex = 0
            history = mutableSetOf<Int>()
            numerator = 0
            denominator = 0
            updateQuestion()
        } else {
            binding.resetButton.visibility = View.VISIBLE
            binding.resetButton.isEnabled = true
        }


    }



    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.v(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy() called")
    }
}
