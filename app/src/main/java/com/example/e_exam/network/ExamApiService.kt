package com.example.e_exam.network

import com.example.e_exam.network.levelsAndDepartments.LevelAndDepartmentRespond
import com.example.e_exam.network.logIn.LogInRespond
import com.example.e_exam.network.logOut.LogOutRespond
import com.example.e_exam.network.signUp.RegisterRespond
import com.example.e_exam.network.studentSubject.GetStudentSubjectRespond
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


//base url of website
private const val BASE_URL =
    "https://e-exam.ahmed-projects.me/api/student/"

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
    @GET("GetLevelsAndDept")
    suspend fun getLevelsAndDepartment(@Query("lang") lang: String): LevelAndDepartmentRespond

    @FormUrlEncoded
    @POST("Register")
    suspend fun createAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("level_id") level_id: Int,
        @Field("dept_id") dept_id: Int
    ): RegisterRespond

    @FormUrlEncoded
    @POST("Login")
    suspend fun logIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): LogInRespond

    @POST("Logout")
    suspend fun logOut(@Header("Authorization") token: String): LogOutRespond

    @GET("Get_Student_Subjects")
    suspend fun getStudentSubject(
        @Query("lang") lang: String,
        @Header("Authorization") token: String
    ): GetStudentSubjectRespond
}

//object to make singleton from the retrofit
object ExamApi {
    val retrofitService: ExamApiService by lazy {
        retrofit.create(ExamApiService::class.java)
    }
}