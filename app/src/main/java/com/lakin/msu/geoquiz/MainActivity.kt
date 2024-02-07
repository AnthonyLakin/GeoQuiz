package com.lakin.msu.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton : Button
    private lateinit var falseButton : Button

/*    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
        Question("The source of the Nile River is in Egypt.", true),
        Question("The Amazon River is the longest river in the Americas", true),

    )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        /*trueButton.setOnClickListener{view: View ->
            Toast.makeText(
                this,
                R.string.true_button,
                Toast.LENGTH_SHORT
            ).show()
        }

        falseButton.setOnClickListener{view: View ->
            Toast.makeText(
                this,
                R.string.false_button,
                Toast.LENGTH_SHORT
            ).show()
        }*/

        trueButton.setOnClickListener{
            val snackBar = Snackbar.make(
                it,
                R.string.correct_toast,
                Snackbar.LENGTH_SHORT
            )
            snackBar.show()
        }

        falseButton.setOnClickListener{
            val snackBar = Snackbar.make(
                it,
                R.string.incorrect_toast,
                Snackbar.LENGTH_SHORT
            )
            snackBar.setTextColor(Color.BLACK)
            snackBar.setBackgroundTint(Color.RED)
            snackBar.show()
        }
    }
}