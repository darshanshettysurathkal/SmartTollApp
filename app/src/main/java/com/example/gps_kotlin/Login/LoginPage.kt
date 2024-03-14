
package com.example.gps_kotlin.Login
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.gps_kotlin.MainActivity
import com.example.gps_kotlin.databinding.ActivityLoginPageBinding
import com.example.gps_kotlin.Login.LoginResponse
import com.example.gps_kotlin.Login.User
import com.example.gps_kotlin.Login.RetrofitClient
import com.example.gps_kotlin.R
import com.example.gps_kotlin.Registration.RegistrationPage
import com.example.gps_kotlin.UserViewModel
import com.example.gps_kotlin.profile.ProfileFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private val apiService = RetrofitClient.apiService
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val username = binding.editTextName.text.toString()
            val vehicleNumber = binding.editTextVehicleNumber.text.toString()
            val user = User(username, vehicleNumber)
            Log.d("LoginPage", "Username: $username, Vehicle Number: $vehicleNumber")

            apiService.loginUser(user).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val statusCode = response.code()
                    when (statusCode) {
                        200 -> {

                            Log.d("LoginPage", "Login successful")
                            viewModel.saveUserDetails(this@LoginPage, username, vehicleNumber)
                            viewModel.userName.value = username
                            viewModel.userVehicleNumber.value = vehicleNumber

                            val userDetailsIntent = Intent(this@LoginPage, MainActivity::class.java)
                            startActivity(userDetailsIntent)
                            finish()

                        }
                        401 -> {
                            // HTTP 401 Unauthorized - Unauthorized access, handle accordingly
                            Toast.makeText(this@LoginPage, "Unauthorized access. Please check your credentials.", Toast.LENGTH_SHORT).show()
                        }
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

        binding.RegristrationPage.setOnClickListener {
            val intent = Intent(this@LoginPage, RegistrationPage::class.java)
            startActivity(intent)
        }
    }
}