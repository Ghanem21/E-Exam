package com.example.e_exam.network.viewExam

import com.squareup.moshi.Json

data class Mcq(
    val id : Int,
    @Json(name = "question_name") val questionName : String,
    @Json(name = "correct_answer") val correctAnswer : String,

)
