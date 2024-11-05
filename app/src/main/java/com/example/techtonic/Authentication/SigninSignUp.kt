package com.example.techtonic.Authentication


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.techtonic.R

class SigninSignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_sign_up)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val signInButton: Button = findViewById(R.id.signInButton)

        // Handle button clicks
        signUpButton.setOnClickListener {
            // Navigate to SignUpActivity (replace with your activity)
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        signInButton.setOnClickListener {
            // Navigate to SignInActivity (replace with your activity)
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}
