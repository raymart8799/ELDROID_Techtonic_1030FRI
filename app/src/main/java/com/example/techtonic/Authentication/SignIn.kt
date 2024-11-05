package com.example.techtonic.Authentication // Replace with your actual package name

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.techtonic.Activity.MainActivity
import com.example.techtonic.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignIn : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)



        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Initialize UI components
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.signInButton)
        registerButton = findViewById(R.id.signup)
//        resetPasswordButton = findViewById(R.id.resetPasswordButton)

        loginButton.setOnClickListener {
            loginUser()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

//        resetPasswordButton.setOnClickListener {
//            val intent = Intent(this, ResetPasswordActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            emailEditText.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            passwordEditText.requestFocus()
            return
        }

        if (password.length < 6) {
            passwordEditText.error = "Password should be at least 6 characters long"
            passwordEditText.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null && user.isEmailVerified) {
                    // Email is verified, allow login
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Email not verified
                    Toast.makeText(this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
//package com.example.techtonic.Authentication
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.techtonic.Activity.MainActivity
//import com.example.techtonic.R
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.android.material.textfield.TextInputEditText
//import com.google.firebase.FirebaseApp
//
//class SignIn : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)
//
//        // Initialize Firebase Auth and Database reference
//        auth = FirebaseAuth.getInstance()
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//
//        val loginButton = findViewById<Button>(R.id.signInButton)
//        loginButton.setOnClickListener {
//            loginUser()
//        }
//    }
//
//    private fun loginUser() {
//        val email = findViewById<TextInputEditText>(R.id.emailEditText).text.toString().trim()
//        val password = findViewById<TextInputEditText>(R.id.passwordEditText).text.toString().trim()
//
//        if (email.isEmpty()) {
//            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        if (password.isEmpty()) {
//            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val user = auth.currentUser
//
//                // Check if email is verified
//                if (user != null && user.isEmailVerified) {
//                    // Email is verified, store user data in Realtime Database
//                    val userId = user.uid
//                    val fullname = intent.getStringExtra("fullname")
//                    val phone = intent.getStringExtra("phone")
//
//                    val userData = mapOf(
//                        "fullname" to fullname,
//                        "email" to email,
//                        "phone" to phone,
//                        "uid" to userId
//                    )
//
//                    // Store data in Firebase Realtime Database
//                    databaseReference.child(userId).setValue(userData)
//                        .addOnSuccessListener {
//                            Toast.makeText(this, "Login successful and data saved!", Toast.LENGTH_LONG).show()
//                            // Redirect to main activity
//                            startActivity(Intent(this, MainActivity::class.java))
//                            finish()
//                        }
//                        .addOnFailureListener { e ->
//                            Toast.makeText(this, "Error saving user data: ${e.message}", Toast.LENGTH_LONG).show()
//                        }
//
//                } else {
//                    Toast.makeText(this, "Please verify your email first.", Toast.LENGTH_LONG).show()
//                }
//
//            } else {
//                Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//}
