package com.example.e_exam.network.viewOldExams

import com.squareup.moshi.Json

data class OldSubject(
    val id : Int,
    @Json(name = "subject_name") val name:String
){
    override fun toString(): String {
        return name
    }
}
