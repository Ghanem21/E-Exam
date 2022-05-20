package com.example.e_exam.network.mcqOptions

import com.squareup.moshi.Json

data class GetMcqOptionsRespond(
    val status: Boolean,
    val errNum: String,
    val msg: String,
    @Json(name  = "Options") val options :List<Option>
)
