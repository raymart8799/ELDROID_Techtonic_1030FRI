package com.example.techtonic.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.techtonic.R
import com.google.firebase.auth.FirebaseAuth

class Verification : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        auth = FirebaseAuth.getInstance()

        // You can implement functionality here to verify the email
        // You could allow users to re-request a verification email or
        // simply show a message after they click a link in the email.
    }

    override fun onResume() {
        super.onResume()

        val user = auth.currentUser
        user?.reload()?.addOnCompleteListener {
            if (user.isEmailVerified) {
                Toast.makeText(this, "Email is verified!", Toast.LENGTH_SHORT).show()
                // Proceed to the next step (e.g., login or main activity)
            } else {
                Toast.makeText(this, "Please verify your email.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

//package com.example.techtonic
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.firestore.FirebaseFirestore
//
//class Verification : AppCompatActivity() {
//
//    private lateinit var codeEditText: EditText
//    private lateinit var verifyButton: Button
//    private lateinit var firestore: FirebaseFirestore
//    private var email: String? = null
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_verification)
//
//        // Initialize Firestore
//        firestore = FirebaseFirestore.getInstance()
//
//        // Get email from the intent
//        email = intent.getStringExtra("email")
//
//        // Initialize UI components
//        codeEditText = findViewById(R.id.codeedittext)
//        verifyButton = findViewById(R.id.verifybutton)
//
//        // Set up button click listener
//        verifyButton.setOnClickListener {
//            verifyCode()
//        }
//    }
//
//    private fun verifyCode() {
//        val enteredCode = codeEditText.text.toString().trim()
//
//        if (enteredCode.isEmpty()) {
//            codeEditText.error = "Code is required"
//            codeEditText.requestFocus()
//            return
//        }
//
//        // Fetch the verification code from Firestore and compare it with the user's input
//        email?.let {
//            firestore.collection("emailVerifications")
//                .document(it)
//                .get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        val storedCode = document.getString("verificationCode")
//                        if (storedCode == enteredCode) {
//                            Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show()
//
//                            // After successful verification, you can proceed to next activity
//                            // e.g., startActivity(Intent(this, MainActivity::class.java))
//                        } else {
//                            Toast.makeText(this, "Invalid verification code", Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        Toast.makeText(this, "No verification code found for this email", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(this, "Error verifying code: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }
//}

