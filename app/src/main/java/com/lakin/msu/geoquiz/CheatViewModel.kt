package com.lakin.msu.geoquiz

import androidx.lifecycle.ViewModel

class CheatViewModel : ViewModel() {
    var isCheater: Boolean = false

    var answerState: Boolean = false

    var buttonClicked: Boolean = false


    fun cheatingSetter(state: Boolean) {
        isCheater = state
    }

    fun answerStateSetter(state: Boolean) {
        answerState = state
    }

    fun buttonClickSetter(state: Boolean) {
        buttonClicked = state
    }
}

