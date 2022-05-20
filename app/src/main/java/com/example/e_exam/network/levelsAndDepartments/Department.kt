package com.example.e_exam.network.levelsAndDepartments

data class Department (
    val id : Int,
    val name : String,
) {
    override fun toString(): String {
        return name
    }
}