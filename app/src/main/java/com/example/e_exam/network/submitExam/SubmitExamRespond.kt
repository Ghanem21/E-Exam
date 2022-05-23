package com.example.e_exam.network.submitExam

import com.squareup.moshi.Json

data class SubmitExamRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name = "grade_info") val grade : Grade
)
