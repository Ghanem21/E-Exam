package com.example.e_exam.network.viewOldAnswers

import com.squareup.moshi.Json

data class McqData(
    @Json(name = "question_name") val question:String,
    @Json(name = "Is_TrueFalse") val isTrue:Int,
)
