package com.example.e_exam.network.mcqOptions

import com.example.e_exam.network.viewExam.Mcq
import com.squareup.moshi.Json

data class Option(
    val id : String,
    @Json(name = "mcq_id") val mcqId : String,
    val answer : String,
    @Json(name = "mcq_question") val mcq : Mcq
)
