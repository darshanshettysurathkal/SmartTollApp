package com.example.gps_kotlin.history

import retrofit2.http.GET

interface MyApi {
    @GET("/api/data")   //mool nina nodejs da endpoint path padre
    suspend fun getData(): List<MyData>
}
