package com.example.techtonic.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.techtonic.Fragments.Report
import com.example.techtonic.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HazardReport : AppCompatActivity() {

    private lateinit var hazardTypeSpinner: Spinner
    private lateinit var cameraButton: Button
    private lateinit var locationEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var hazardPicture: ImageView
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var database: DatabaseReference
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private var currentAddress: String? = null
    private var imageUri: Uri? = null // Store URI for image upload

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hazard_report)

        // Initialize Firebase Realtime Database reference
        database = FirebaseDatabase.getInstance().reference
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request permissions
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
            ),
            1
        )

        // Initialize UI components
        hazardTypeSpinner = findViewById(R.id.spinnerHazardType)
        cameraButton = findViewById(R.id.btnCamera)
        locationEditText = findViewById(R.id.edtLocation)
        descriptionEditText = findViewById(R.id.edtDescription)
        submitButton = findViewById(R.id.btnSubmit)
        hazardPicture = findViewById(R.id.hazardpic)

        // Set up spinner
        val hazardTypes = arrayOf("Pothole", "Debris", "Wires", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hazardTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hazardTypeSpinner.adapter = adapter

        // Initialize camera launcher
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap: Bitmap? = result.data?.extras?.get("data") as? Bitmap
                bitmap?.let {
                    hazardPicture.setImageBitmap(it)
                    imageUri = saveImageAndGetUri(it)
                    if (imageUri != null) {
                        Log.d("HazardReport", "Image URI set successfully: $imageUri")
                    } else {
                        Log.e("HazardReport", "Failed to generate image URI")
                    }
                    getLocation()
                }
            }
        }

        cameraButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }

        submitButton.setOnClickListener {
            if (validateInput()) {
                submitReport()
            }
        }
    }

    // Helper to save image and get Uri
    private fun saveImageAndGetUri(bitmap: Bitmap): Uri? {
        return try {
            val tempFile = File.createTempFile("hazard_image", ".jpg", cacheDir)
            val outputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Log.d("HazardReport", "Image saved to temporary file successfully: ${tempFile.absolutePath}")
            Uri.fromFile(tempFile)
        } catch (e: IOException) {
            Log.e("HazardReport", "Failed to save image to temporary file", e)
            null
        }
    }

    private fun uploadImageToFirebase(uri: Uri, reportId: String) {
        Log.d("HazardReport", "Attempting to upload image to Firebase with URI: $uri")
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$reportId.jpg")

        storageRef.putFile(uri)
            .addOnSuccessListener {
                Log.d("HazardReport", "Image uploaded to Firebase Storage successfully")
                storageRef.downloadUrl.addOnSuccessListener { url ->
                    database.child("hazardReports/$reportId/imageUrl").setValue(url.toString())
                        .addOnSuccessListener {
                            Log.d("HazardReport", "Image URL saved to database successfully: $url")
                            Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.e("HazardReport", "Error saving image URL to database", e)
                        }
                }.addOnFailureListener { e ->
                    Log.e("HazardReport", "Error retrieving download URL", e)
                    Toast.makeText(this, "Image upload failed: Unable to get URL", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("HazardReport", "Image upload to Firebase Storage failed: ${e.message}", e)
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
    }


    private fun getCurrentDateTime(): String {
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            currentLocation = location
            if (location != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    currentAddress = addresses[0].getAddressLine(0)
                    locationEditText.setText(currentAddress)
                }
            } else {
                Toast.makeText(this, "Please turn on your Location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(): Boolean {
        return when {
            locationEditText.text.isNullOrEmpty() -> {
                Toast.makeText(this, "Location cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            descriptionEditText.text.isNullOrEmpty() -> {
                Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun submitReport() {
        val hazardType = hazardTypeSpinner.selectedItem.toString()
        val location = locationEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val timestamp = getCurrentDateTime()
        val reportId = database.child("hazardReports").push().key ?: return

        val report = mapOf(
            "hazardType" to hazardType,
            "location" to location,
            "description" to description,
            "timestamp" to timestamp
        )


        database.child("hazardReports").child(reportId).setValue(report)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (imageUri != null) {
                        uploadImageToFirebase(imageUri!!, reportId)
                    } else {
                        Toast.makeText(this, "Report submitted without an image", Toast.LENGTH_SHORT).show()
                    }
                    clearFields()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_report, Report()) // Use the correct container ID
                        .addToBackStack(null) // Optional: Add to back stack to allow returning to the report activity
                        .commit()
                } else {
                    Toast.makeText(this, "Error submitting report", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun clearFields() {
        locationEditText.text.clear()
        descriptionEditText.text.clear()
        hazardTypeSpinner.setSelection(0)
        hazardPicture.setImageDrawable(null)
    }
}

