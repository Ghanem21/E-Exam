package com.example.e_exam.network.viewExam

import com.squareup.moshi.Json

data class Question(
    val id :Int,
    @Json(name = "exam_id") val examId : String,
    @Json(name = "mcq_id") val mcqId : String,
    val mcq : Mcq
)
