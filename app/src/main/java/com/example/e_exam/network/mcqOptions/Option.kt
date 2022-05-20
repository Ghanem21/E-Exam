package com.example.e_exam.network.mcqOptions

import com.squareup.moshi.Json

data class Option(
    val id : String,
    @Json(name = "mcq_id") val mcqId : String,
    val answer : String
)
