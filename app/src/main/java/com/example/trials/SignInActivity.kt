package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trials.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Hardcoded admin credentials
    private val adminEmail = "admin@example.com"
    private val adminPassword = "adminPassword123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Navigate to SignUpActivity
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Handle the sign-in process
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            // Validate email and password input
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Validate if a user type is selected
                val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
                if (selectedRadioButtonId == -1) {
                    Toast.makeText(this, "Please select a user type", Toast.LENGTH_LONG).show()
                } else {
                    // Get selected user type
                    val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                    val userType = selectedRadioButton?.text.toString()

                    // Handle sign-in based on user type
                    when (userType) {
                        "Company" -> signInUser(email, pass, CompanyDash::class.java)
                        "Seeker" -> signInUser(email, pass, InquiryMainActivity::class.java)
                        "Admin" -> {
                            // Check if the hardcoded admin credentials match
                            if (email == adminEmail && pass == adminPassword) {
                                // Hardcoded credentials matched, go to AdminDash
                                val intent = Intent(this, AdminDash::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Invalid admin credentials", Toast.LENGTH_LONG).show()
                            }
                        }
                        else -> Toast.makeText(this, "Invalid user type selected", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed!!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Reusable function to handle sign-in and navigation
    private fun <T> signInUser(email: String, password: String, destinationActivity: Class<T>) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Redirect to the respective dashboard and finish the current activity
                val intent = Intent(this, destinationActivity)
                startActivity(intent)
                finish()
            } else {
                // Display a more user-friendly error message
                Toast.makeText(this, task.exception?.localizedMessage ?: "Login failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }
}

