package com.example.e_exam.network.viewExam

import com.squareup.moshi.Json

data class ExamQuestionRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name = "The Questions Exam") val questions : List<Question>?
)
