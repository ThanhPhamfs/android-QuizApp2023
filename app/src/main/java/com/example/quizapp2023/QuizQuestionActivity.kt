package com.example.quizapp2023

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private var score: Int = 0
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var isSubmitted = false
    private var name: String? = null
    private var progressBar: ProgressBar? = null
    private var tvProgressBar: TextView? = null
    private var tvQuestion: TextView? = null
    private var imageViewQuestion: ImageView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        name = intent.getStringExtra("name").toString()
        Toast.makeText(this, name, Toast.LENGTH_LONG).show()

        setControl()
        mQuestionsList = Constant.getQuestions()
        setQuestion()
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
    }

    //    Set new question on view
    private fun setQuestion() {
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        tvProgressBar?.text = "$mCurrentPosition / ${progressBar?.max}"
        tvQuestion?.text = question!!.question
        imageViewQuestion?.setImageResource(question.image)
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if (mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit?.text = Constant.SubmitButtonState.FINISH.value
        } else {
            btnSubmit?.text = Constant.SubmitButtonState.SUBMIT.value
        }
    }

    //    Set control
    private fun setControl() {
        progressBar = findViewById(R.id.progressBar)
        tvProgressBar = findViewById(R.id.tvProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        imageViewQuestion = findViewById(R.id.imageViewQuestion)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
    }

    //    Set all textview to default state
    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3, it)
        }
        for (option in options) {
            option.setTextColor(Color.parseColor("#897A7A"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    //    selected option view
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    //    Set correct option view
    private fun correctOptionView(tv: TextView) {
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.correct_option_border_bg
        )
    }

    //Set wrong option view
    private fun wrongOptionView(tv: TextView) {
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.ITALIC)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.wrong_option_border_bg
        )
    }

    //    Handle on Click
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> {
                if (isSubmitted) {
                    return
                }
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_option_two -> {
                if (isSubmitted) {
                    return
                }
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_option_three -> {
                if (isSubmitted) {
                    return
                }
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_option_four -> {
                if (isSubmitted) {
                    return
                }
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_submit -> {
                handlingSubmitButton()
            }
        }
    }

    private fun handlingSubmitButton() {
        when (btnSubmit?.text) {
//            When user have not submited
            Constant.SubmitButtonState.SUBMIT.value -> {
                if (mSelectedOptionPosition == 0) {
                    Toast.makeText(this, "Please select 1 option!", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                val question: Question = mQuestionsList!![mCurrentPosition - 1]
                when (question.correctAnswer) {
                    1 -> tvOptionOne?.let { correctOptionView(it) }
                    2 -> tvOptionTwo?.let { correctOptionView(it) }
                    3 -> tvOptionThree?.let { correctOptionView(it) }
                    4 -> tvOptionFour?.let { correctOptionView(it) }
                }
//                Set wrong state for textview if select wrong option
                if (question.correctAnswer != mSelectedOptionPosition) {
                    when (mSelectedOptionPosition) {
                        1 -> tvOptionOne?.let { wrongOptionView(it) }
                        2 -> tvOptionTwo?.let { wrongOptionView(it) }
                        3 -> tvOptionThree?.let { wrongOptionView(it) }
                        4 -> tvOptionFour?.let { wrongOptionView(it) }
                    }
                } else {
//                    Increase score when selecting correct option
                    score++
                }

                isSubmitted = true
                if (mCurrentPosition == mQuestionsList!!.size) {
                    btnSubmit?.text = Constant.SubmitButtonState.FINISH.value
                } else {
                    btnSubmit?.text = Constant.SubmitButtonState.GO_TO_NEXT_QUESTION.value
                    mCurrentPosition++
                }
            }
            Constant.SubmitButtonState.GO_TO_NEXT_QUESTION.value -> {
                setQuestion()
                defaultOptionsView()
                mSelectedOptionPosition = 0
                btnSubmit?.text = "Submit"
                isSubmitted = false
            }
            Constant.SubmitButtonState.FINISH.value -> {
                var pro: Constant.Profile? = name?.let { Constant.Profile(it, score) }
                pro?.let {
                    startActivity(Intent(this, ResultActivity::class.java).apply {
                        putExtra("profile", pro)
                    })
                    finish()
                }

            }
        }
    }
}