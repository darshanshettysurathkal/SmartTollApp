package com.example.gps_kotlin.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gps_kotlin.MainActivity
import com.example.gps_kotlin.databinding.ActivityLoginPageBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val vname = binding.editTextName.text.toString()
            val vpassword = binding.editTextVehicleNumber.text.toString()
            val user = User(vname, vpassword)



            // Convert the User object to JSON string for logging
            val gson = Gson()
            val userJson = gson.toJson(user)

            // Log the JSON string
            Log.d("UserData", "User JSON: $userJson")

            apiService.loginUser(user).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val statusCode = response.code()
                    when (statusCode) {
                        200 -> {
                            // HTTP 200 OK - Login successful
                                startActivity(Intent(this@LoginPage, MainActivity::class.java))
                                finish()

                        }
                        401 -> {
                            // HTTP 401 Unauthorized - Unauthorized access, handle accordingly
                            Toast.makeText(this@LoginPage, "Unauthorized access. Please check your credentials.", Toast.LENGTH_SHORT).show()
                        }
                        // Add more cases as needed for other response codes
                        else -> {
                            // Other HTTP status codes - Handle accordingly
                            Toast.makeText(this@LoginPage, "Unexpected error. Please try again later.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Network error
                    Toast.makeText(this@LoginPage, "Network error. Please try again.", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}
