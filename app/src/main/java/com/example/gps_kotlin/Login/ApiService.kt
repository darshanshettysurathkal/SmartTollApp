package com.example.gps_kotlin.Login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginResponse(val isSuccess: Boolean)

interface ApiService {
    @POST("user/login")
    fun loginUser(@Body user: User): Call<LoginResponse>
}
