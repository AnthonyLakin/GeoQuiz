package com.lakin.msu.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
private  const val TAG = "QuizViewModel"
class QuizViewModel:ViewModel() {

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


    val getQuestionBank: List<Question>
        get() = questionBank

    val getCurrentIndex: Int
        get() = currentIndex

    val getHistory: MutableSet<Int>
        get() = history
    val getNumerator: Int
        get() = numerator
    val getDenominator: Int
        get() = denominator



    fun percentHandler(type: String) {
        when (type) {
            "top" -> {
                numerator++
            }
            "bottom" -> {
                denominator++
            }

        }
    }

    fun currentIndexHandler(stage: Int) {
        when (stage) {
            0 -> {
                currentIndex++
            }
            1 -> {
                currentIndex--
            }
            2 -> {
                currentIndex = 0
            }
        }
    }



    fun addToHistory(index: Int) {
        history.add(index)
    }

    fun resetVars() {
        currentIndex = 0
        history = mutableSetOf()
        numerator = 0
        denominator = 0
    }


}