package com.example.gps_kotlin

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userName = MutableLiveData<String>()
    val userVehicleNumber = MutableLiveData<String>()

    // SharedPreferences
    private val PREF_NAME = "UserPrefs"
    private val KEY_USER_NAME = "userName"
    private val KEY_VEHICLE_NUMBER = "vehicleNumber"

    private lateinit var sharedPreferences: SharedPreferences

    fun saveUserDetails(context: Context, userName: String, vehicleNumber: String) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_NAME, userName)
        editor.putString(KEY_VEHICLE_NUMBER, vehicleNumber)
        editor.apply()
    }

    fun getUserNameFromLocalStorage(context: Context): String? {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    fun getVehicleNumberFromLocalStorage(context: Context): String? {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_VEHICLE_NUMBER, null)
    }
}
