package com.example.gps_kotlin.Registration
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import okhttp3.*
import java.io.IOException
import org.json.JSONObject

class APIreg {

    companion object {
        private const val BASE_URL = "https://smart-toll.onrender.com/"
        private const val MAX_RETRIES = 5
        private const val RETRY_DELAY_MS = 1000 // 1 second delay between retries
    }

    private val client = OkHttpClient()

    fun sendVehicleNumber(vehicleNumber: String, callback: ApiCallback, retryCount: Int = 0) {
        val jsonObject = JSONObject()
        jsonObject.put("registrationNumber", vehicleNumber)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        val request = Request.Builder()
            .url(BASE_URL + "user/vehicle")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (retryCount < MAX_RETRIES) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        sendVehicleNumber(vehicleNumber, callback, retryCount + 1)
                    }, RETRY_DELAY_MS.toLong())
                } else {
                    callback.onFailure(e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    if (responseBody != null) {
                        callback.onSuccess(responseBody)
                    } else {
                        callback.onFailure(IOException("Empty response body"))
                    }
                } else if (response.code() == 401) {
                    callback.onFailure(IOException("Unauthorized access"))
                } else {
                    callback.onFailure(IOException("Unexpected Error"))
                }
            }
        })
    }


    fun sendNumber(phoneNumber: String) {
        val jsonObject = JSONObject()
        jsonObject.put("phoneNumber", phoneNumber)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        val request = Request.Builder()
            .url(BASE_URL + "user/send-otp")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO()
            }

            override fun onResponse(call: Call, response: Response) {
                // No need to handle response as it's fire-and-forget
            }
        })
    }

    fun sendOtpAndPhoneNumber(otp: String, phoneNumber: String, callback: ApiCallback) {
        val jsonObject = JSONObject().apply {
            put("otp", otp)
            put("phoneNumber", phoneNumber)
        }

        val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        val request = Request.Builder()
            .url(BASE_URL + "user/verify-otp")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    if (responseBody != null) {
                        callback.onSuccess(responseBody)
                    } else {
                        callback.onFailure(IOException("Empty response body"))
                    }
                } else {
                    callback.onFailure(IOException("Unexpected code ${response.code()}"))
                }
            }
        })
    }


    interface ApiCallback {
        fun onSuccess(responseBody: String)
        fun onFailure(e: Exception)
    }
}
