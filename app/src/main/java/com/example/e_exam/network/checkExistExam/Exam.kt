package com.example.e_exam.network.checkExistExam

import com.squareup.moshi.Json

data class Exam(
    val id : Int,
    @Json(name = "exam_name") val name:String,
    @Json(name = "subject_id") val subjectId : String,
    @Json(name = "prof_id") val professorId : String,
    @Json(name = "start_at") val startTime : String,
    val duration : String,
    @Json(name = "end_at") val endTime : String
)
