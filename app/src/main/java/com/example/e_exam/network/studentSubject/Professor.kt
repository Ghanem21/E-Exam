package com.example.e_exam.network.studentSubject

data class Professor(
    val id : Int,
    val name:String
){
    override fun toString(): String {
        return name
    }
}
