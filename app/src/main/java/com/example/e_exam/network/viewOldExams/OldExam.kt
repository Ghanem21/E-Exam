package com.example.e_exam.network.viewOldExams

import com.example.e_exam.network.studentSubject.Professor
import com.squareup.moshi.Json

data class OldExam(
    val id: Int,
    @Json(name = "exam_name") val name: String,
    @Json(name = "subject_id") val subjectId: String,
    @Json(name = "prof_id") val professorId: String,
    @Json(name = "subjects")val subject: OldSubject,
    @Json(name = "professors")val professor: Professor,
)