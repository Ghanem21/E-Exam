package com.example.e_exam.network.viewOldExams

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import java.text.SimpleDateFormat
import java.util.*

data class Exams(
    val id: Int,
    @Json(name = "exam_id") val examId: String,
    @Json(name = "student_id") val studentId: String,
    @Json(name = "exam_grade") val total: String,
    @Json(name = "student_grade") val scored: String,
    @Json(name = "created_at") val createDate: String,
    @Json(name = "updated_at") val updateDate: String,
    val exam: OldExam,

    ) {
    fun getDate(): String {
        return getDateFromMilliseconds(updateDate, "dd/MM/yyyy\nHH:mm")
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateFromMilliseconds(time: String, dataFormat: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat(dataFormat, Locale.getDefault())
        return formatter.format(parser.parse(time)!!)
    }

    fun getGrade(): String {
        return "$scored/$total"
    }
}
