package com.example.gps_kotlin.Registration


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gps_kotlin.Registration.vehicle_details.VehicleDetails
import com.example.gps_kotlin.databinding.ActivityRegistrationPageBinding
import com.example.gps_kotlin.Registration.APIreg
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RegistrationPage : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonSubmitRegPage.setOnClickListener {
            val vehicleNumber = binding.editTextVehicleNumber.text.toString()
            sendVehicleNumberToServer(vehicleNumber)
        }
    }

    private fun sendVehicleNumberToServer(vehicleNumber: String) {
        Log.d("RegistrationPage", "Sending vehicle number to server: $vehicleNumber") // Log the data being sent to the server

        APIreg().sendVehicleNumber(vehicleNumber, object : APIreg.ApiCallback {
            override fun onSuccess(responseBody: String) {
                runOnUiThread {
                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val registrationNumber = jsonResponse.getString("RegistrationNumber")
                        val registrationDate = jsonResponse.getString("RegistrationDate")
                        val chassisNumber = jsonResponse.getString("ChasisNumber")
                        val engineNumber = jsonResponse.getString("EngineNumber")
                        val ownerName = jsonResponse.getString("OwnerName")
                        val phoneNumber = jsonResponse.getString("PhoneNumber")

                        // Create intent to pass data to the next page
                        val intent = Intent(this@RegistrationPage, VehicleDetails::class.java)
                        intent.putExtra("registrationNumber", registrationNumber)
                        intent.putExtra("registrationDate", registrationDate)
                        intent.putExtra("chassisNumber", chassisNumber)
                        intent.putExtra("engineNumber", engineNumber)
                        intent.putExtra("ownerName", ownerName)
                        intent.putExtra("phoneNumber", phoneNumber)
                        startActivity(intent)
                    } catch (e: JSONException) {
                        Toast.makeText(this@RegistrationPage, "Failed to parse server response.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(e: Exception) {
                runOnUiThread {
                    // Handle failure response here
                    if (e is IOException && e.message == "Unauthorized access") {
                        // Handle unauthorized access error
                        Toast.makeText(this@RegistrationPage, "Unauthorized access. Please check your credentials.", Toast.LENGTH_SHORT).show()
                    } else if(e is IOException && e.message == "Empty response body") {
                        // Handle other failure cases or unexpected errors

                        Toast.makeText(this@RegistrationPage, "Empty Response Body.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@RegistrationPage, "Unexpected error. Please try again later.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

}
