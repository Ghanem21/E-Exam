package com.example.e_exam.network.studentSubject

import com.squareup.moshi.Json

data class GetStudentSubjectRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name = "Subjects Student") val subject : List<Subject>?
)
