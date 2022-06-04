package com.example.e_exam.network.viewOldAnswers

import com.example.e_exam.network.viewOldExams.Exams
import com.squareup.moshi.Json

data class ViewOldAnswerRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name = "Exam Questions") val exams : List<Answer>?
)
