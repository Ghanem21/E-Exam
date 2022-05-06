package com.example.e_exam.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//base url of website
private const val BASE_URL =
    ""

//moshi build which we will use to convert json to object kotlin
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//retrofit build which use to get json response from the base url
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ExamApiService {
}

//object to make singleton from the retrofit
object ExamApi {
    val retrofitService: ExamApiService by lazy {
        retrofit.create(ExamApiService::class.java)
    }
}