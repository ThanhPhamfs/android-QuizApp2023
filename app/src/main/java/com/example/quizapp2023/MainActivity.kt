package com.example.quizapp2023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    var editTextName: AppCompatEditText? = null
    var btnStart: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextName = findViewById(R.id.editTextName)
        btnStart = findViewById(R.id.btnStart)


        btnStart?.setOnClickListener {view -> handlingStart(view)
        }
    }

    private fun handlingStart(view: View) {
        val text = editTextName?.text.toString()
        editTextName?.let {
            if (text.isEmpty()){
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this, QuizQuestionActivity::class.java).apply {
                    putExtra("name", text)
                })
                finish()
            }
            return
        }
        Toast.makeText(this, "Do not find edit text", Toast.LENGTH_SHORT).show()
    }
}