package com.example.quizapp2023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class ResultActivity : AppCompatActivity(), View.OnClickListener {
    private var profile: Constant.Profile? = null
    private var tv_name: TextView? = null
    private var tv_score: TextView? = null
    private var btn_finish: AppCompatButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        profile = intent.getSerializableExtra("profile") as? Constant.Profile


        tv_name = findViewById(R.id.tv_name)
        tv_score = findViewById(R.id.tv_score)
        btn_finish = findViewById(R.id.btn_finish)
        profile?.let {
//            Toast.makeText(this, "${it.username} with ${it.score}", Toast.LENGTH_SHORT).show()
            tv_name?.text = it.username
            tv_score?.text = "Your Score is ${it.score} out of 10"
        }
        btn_finish?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_finish -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}