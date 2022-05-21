package com.example.e_exam.network.submitExam

import com.squareup.moshi.Json

data class Grade(
    @Json(name = "exam_grade") val total : Int,
    @Json(name = "student_grade") val scored : Int,

)
