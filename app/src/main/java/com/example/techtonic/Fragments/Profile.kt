package com.example.techtonic.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.techtonic.R


class Profile : Fragment() {
    private lateinit var ivProfileImage: ImageView
    private lateinit var btnChangeImage: Button
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etBdate: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize UI elements
        ivProfileImage = view.findViewById(R.id.iv_profile_image)
        btnChangeImage = view.findViewById(R.id.btn_change_image)
        etName = view.findViewById(R.id.et_name)
        etEmail = view.findViewById(R.id.et_email)
        etBdate = view.findViewById(R.id.et_bdate)
        etPhone = view.findViewById(R.id.et_phone)
        btnSave = view.findViewById(R.id.btn_save)

        // Set up change image button listener
        btnChangeImage.setOnClickListener {
            openImagePicker()
        }

        // Set up save button listener
        btnSave.setOnClickListener {
            saveProfile()
        }

        return view
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
            ivProfileImage.setImageBitmap(bitmap)
        }
    }

    // Save profile function (you can save to SharedPreferences, Database, etc.)
    private fun saveProfile() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val birthdate = etBdate.text.toString()
        val phone = etPhone.text.toString()

        if (name.isBlank() || email.isBlank() || birthdate.isBlank() || phone.isBlank()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            // Here, you could save the data to SharedPreferences, database, or backend
            Toast.makeText(requireContext(), "Profile Saved", Toast.LENGTH_SHORT).show()

            // Optionally disable fields after saving to simulate "view mode"
            setFieldsEnabled(false)
            btnSave.text = "Edit Profile"
            btnSave.setOnClickListener { enableEditing() }
        }
    }

    // Enable fields for editing
    private fun enableEditing() {
        setFieldsEnabled(true)
        btnSave.text = "Save Profile"
        btnSave.setOnClickListener { saveProfile() }
    }

    // Helper function to enable/disable fields
    private fun setFieldsEnabled(enabled: Boolean) {
        etName.isEnabled = enabled
        etEmail.isEnabled = enabled
        etBdate.isEnabled = enabled
        etPhone.isEnabled = enabled
        btnChangeImage.isEnabled = enabled
    }

}