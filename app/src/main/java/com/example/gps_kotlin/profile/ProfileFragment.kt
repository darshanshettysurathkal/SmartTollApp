package com.example.gps_kotlin.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gps_kotlin.R
import com.example.gps_kotlin.UserViewModel

class ProfileFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var imageViewProfilePhoto: ImageView
    private lateinit var uploadPhotobutton: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        val textViewUserName: TextView = view.findViewById(R.id.username)
        val textViewVehicleNumber: TextView = view.findViewById(R.id.vehiclenumber)
        uploadPhotobutton = view.findViewById(R.id.uploadButton)
        imageViewProfilePhoto = view.findViewById(R.id.imageViewProfilePhoto)

        // Retrieve user information from SharedPreferences or any other local storage mechanism
        val userName = viewModel.getUserNameFromLocalStorage(requireContext())
        val vehicleNumber = viewModel.getVehicleNumberFromLocalStorage(requireContext())

        // Display user information in TextViews
        textViewUserName.text = userName
        textViewVehicleNumber.text = vehicleNumber

        // Set click listener for upload photo button
        uploadPhotobutton.setOnClickListener {
            openGallery()
        }

        return view
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            imageViewProfilePhoto.setImageURI(selectedImageUri)
            // You may want to save the selected image URI for future use
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
