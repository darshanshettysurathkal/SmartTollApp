package com.example.gps_kotlin.Registration.vehicle_details

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gps_kotlin.Registration.APIreg
import com.example.gps_kotlin.Registration.OTP
import com.example.gps_kotlin.databinding.ActivityVehicleDetailsBinding
import org.json.JSONObject

class VehicleDetails : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityVehicleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve data sent from Registration page through intent
        val registrationNumber = intent.getStringExtra("registrationNumber")
        val registrationDate = intent.getStringExtra("registrationDate")
        val chassisNumber = intent.getStringExtra("chassisNumber")
        val engineNumber = intent.getStringExtra("engineNumber")
        val ownerName = intent.getStringExtra("ownerName")
        val phone = intent.getStringExtra("phoneNumber")

        // Display the retrieved data
        binding.textRegistrationNumber.text = "Registration Number: $registrationNumber"
        binding.textRegistrationDate.text = "Registration Date: $registrationDate"
        binding.textChassisNumber.text = "Chassis Number: $chassisNumber"
        binding.textEngineNumber.text = "Engine Number: $engineNumber"
        binding.textOwnerName.text = "Owner Name: $ownerName"
        binding.textPhoneNumber.text ="Phone Number: $phone"

        binding.confirmDetailsButton.setOnClickListener {
            sendPhoneNumberToServer(phone.toString())
        }


    }

    private fun sendPhoneNumberToServer(phoneNumber: String) {
        // Instantiate APIreg class
        val api = APIreg()

        // Send the phone number to the server using the API
        api.sendNumber(phoneNumber)
        // Display a toast message to indicate that the phone number has been sent
        val intent = Intent(this@VehicleDetails, OTP::class.java)
        intent.putExtra("phoneNumber", phoneNumber)
        startActivity(intent)
        // You can also finish this activity or navigate to another activity if needed
    }
}
