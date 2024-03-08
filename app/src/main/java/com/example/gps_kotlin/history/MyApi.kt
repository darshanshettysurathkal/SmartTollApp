package com.example.gps_kotlin.history

import retrofit2.http.GET

interface MyApi {
    @GET("/api/data")
    suspend fun getData(): List<MyData>
}
