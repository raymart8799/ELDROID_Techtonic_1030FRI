////package com.example.techtonic.Authentication
////
////import android.annotation.SuppressLint
////import android.content.Intent
////import android.os.Bundle
////import android.util.Patterns
////import android.widget.Button
////import android.widget.EditText
////import android.widget.Toast
////import androidx.appcompat.app.AppCompatActivity
////import com.example.techtonic.R
////import com.google.firebase.auth.FirebaseAuth
////import com.google.firebase.firestore.FirebaseFirestore
////
////class SignUp : AppCompatActivity() {
////
////    private lateinit var fullnameEditText: EditText
////    private lateinit var phoneEditText: EditText
////    private lateinit var emailEditText: EditText
////    private lateinit var passwordEditText: EditText
////    private lateinit var confirmPasswordEditText: EditText
////    private lateinit var registerButton: Button
////    private lateinit var auth: FirebaseAuth
////    private lateinit var firestore: FirebaseFirestore
////
////    @SuppressLint("MissingInflatedId")
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_sign_up)
////
////        // Initialize Firebase Auth and Firestore
////        auth = FirebaseAuth.getInstance()
////        firestore = FirebaseFirestore.getInstance()
////
////        // Initialize UI components
////        fullnameEditText = findViewById(R.id.fullnameEditText)
////        emailEditText = findViewById(R.id.emailEditText)
////        phoneEditText = findViewById(R.id.phoneEditText)
////        passwordEditText = findViewById(R.id.passEditText)
////        confirmPasswordEditText = findViewById(R.id.conpassEditText)
////        registerButton = findViewById(R.id.registerButton)
////
////        // Set up button click listener
////        registerButton.setOnClickListener {
////            registerUser()
////        }
////    }
////
////    private fun registerUser() {
////        val fullname = fullnameEditText.text.toString().trim()
////        val email = emailEditText.text.toString().trim()
////        val phone = phoneEditText.text.toString().trim()
////        val password = passwordEditText.text.toString().trim()
////        val confirmPassword = confirmPasswordEditText.text.toString().trim()
////
////        // Validate input
////        if (fullname.isEmpty()) {
////            fullnameEditText.error = "Fullname is required"
////            fullnameEditText.requestFocus()
////            return
////        }
////
////        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
////            emailEditText.error = "Enter a valid email"
////            emailEditText.requestFocus()
////            return
////        }
////
////        if (phone.isEmpty() || phone.length != 11) {
////            phoneEditText.error = "Enter a valid phone number"
////            phoneEditText.requestFocus()
////            return
////        }
////
////        if (password.isEmpty() || password.length < 6) {
////            passwordEditText.error = "Password must be at least 6 characters"
////            passwordEditText.requestFocus()
////            return
////        }
////
////        if (password != confirmPassword) {
////            confirmPasswordEditText.error = "Passwords do not match"
////            confirmPasswordEditText.requestFocus()
////            return
////        }
////
////        // Create user with Firebase Authentication
////        auth.createUserWithEmailAndPassword(email, password)
////            .addOnCompleteListener { task ->
////                if (task.isSuccessful) {
////                                       // Send email verification
////                    val user = auth.currentUser
////                    user?.sendEmailVerification()
////                        ?.addOnCompleteListener { verificationTask ->
////                            if (verificationTask.isSuccessful) {
////                                Toast.makeText(this, "Verification email sent to $email", Toast.LENGTH_SHORT).show()
////
////                                // Redirect to the login or verification activity
////                                val intent = Intent(this, SignIn::class.java)
////                                startActivity(intent)
////                            } else {
////                                Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
////                            }
////                        }
////                } else {
////                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
////                }
////            }
////    }
////}
////
////
package com.example.techtonic.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.techtonic.R
import com.example.techtonic.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User

class SignUp : AppCompatActivity() {


    private lateinit var fullnameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Initialize UI components
        fullnameEditText = findViewById(R.id.fullnameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        passwordEditText = findViewById(R.id.passEditText)
        confirmPasswordEditText = findViewById(R.id.conpassEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        // Set up button click listener
        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val fullname = fullnameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Validate input
        if (fullname.isEmpty()) {
            fullnameEditText.error = "Fullname is required"
            fullnameEditText.requestFocus()
            return
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Enter a valid email"
            emailEditText.requestFocus()
            return
        }

        if (phone.isEmpty() || phone.length != 10) {
            phoneEditText.error = "Enter a valid phone number"
            phoneEditText.requestFocus()
            return
        }

        if (password.isEmpty() || password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            passwordEditText.requestFocus()
            return
        }

        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            confirmPasswordEditText.requestFocus()
            return
        }

        // Create user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveUserData(email, fullname, phone)
                // Send verification email
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { verifyTask ->
                    if (verifyTask.isSuccessful) {
                        // Show a message to the user to verify their email
                        Toast.makeText(
                            this,
                            "Registration successful. Please check your email for verification.",
                            Toast.LENGTH_LONG
                        ).show()

                        // Move to the login screen or stay on the current screen
                        startActivity(Intent(this, SignIn::class.java))
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "Error in sending verification email: ${verifyTask.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Registration failed: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveUserData(email: String, fullName: String, phoneNumber: String) {
        val userId = auth.currentUser?.uid
        Log.d("Firebase", "Saving data for user ID: $userId")

        if (userId == null) {
            Log.w("Firebase", "User ID is null. Cannot save data.")
            return
        }

        // Create a new node in the database using the user's UID
        val userRef = database.child("users").child(userId)

        // Create an instance of the User data class
        val userData = Users(email, fullName, phoneNumber)

        Log.d("Firebase", "User data: $userData")

        // Save the user data to the database
        userRef.setValue(userData)
            .addOnSuccessListener {
                Log.d("Firebase", "User data saved successfully")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error saving user data", e)
            }
    }

}



