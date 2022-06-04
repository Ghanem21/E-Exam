package com.example.e_exam.network.viewOldAnswers

import com.squareup.moshi.Json

data class Answer(
    @Json(name = "student_answer") val studentAnswer:String,
    @Json(name = "correct_answer") val correctAnswer:String,
    @Json(name = "mcq") val mcqData:McqData

){
    fun isCorrect():Boolean{
        return studentAnswer == correctAnswer
    }
}
