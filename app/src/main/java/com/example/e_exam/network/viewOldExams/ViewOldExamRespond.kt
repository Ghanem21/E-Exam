package com.example.e_exam.network.viewOldExams

import com.squareup.moshi.Json

data class ViewOldExamRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name = "Exams Student") val exams : List<Exams>?
)
