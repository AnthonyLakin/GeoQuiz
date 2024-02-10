package com.lakin.msu.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lakin.msu.geoquiz.databinding.ActivityMainBinding


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }
        //        Exercise 2 snippet below, clicking on text view advances questions
        binding.questionTextview.setOnClickListener {
            questionHandler("next")
        }
        binding.nextButton.setOnClickListener {
            questionHandler("next")
        }
        //    Exercise 3 snippet below, added previous functionality
        binding.prevButton.setOnClickListener {
            questionHandler("prev")
        }


    }

    //    Exercise 2 snippet below, clicking on text view advances questions
    private fun questionHandler(typeState: String) {
        if (typeState == "next") {
            currentIndex = (currentIndex + 1) % questionBank.size

            //    Exercise 3 snippet below, added previous functionality
        } else if (typeState == "prev") {
            if (currentIndex != 0 ) {
                currentIndex = (currentIndex - 1) % questionBank.size
            }


        }
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)
    }
}
