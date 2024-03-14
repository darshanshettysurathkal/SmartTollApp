package com.example.gps_kotlin.Registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gps_kotlin.MainActivity
import com.example.gps_kotlin.Registration.APIreg
import com.example.gps_kotlin.databinding.ActivityOtpBinding
import com.example.gps_kotlin.home.Home_Page

class OTP : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private val api = APIreg()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonSubmitOTP.setOnClickListener {
            val otp = binding.editTextOTP.text.toString()
            val phoneNumber = intent.getStringExtra("phoneNumber") ?: ""

            api.sendOtpAndPhoneNumber(otp, phoneNumber, object : APIreg.ApiCallback {
                override fun onSuccess(responseBody: String) {
                    runOnUiThread {

                       val intent = Intent(this@OTP, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@OTP,
                            "Failed to send OTP and phone number",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Handle failure as needed
                    }
                }
            })
        }
    }
}
