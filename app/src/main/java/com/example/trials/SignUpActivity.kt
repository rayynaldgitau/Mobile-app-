package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trials.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {
    // Declare variables and references
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var registeredUsersRef: DatabaseReference
    private lateinit var usersRef: DatabaseReference
    private var totalRegisteredUsers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the activity's layout using ViewBinding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication and the database reference
        firebaseAuth = FirebaseAuth.getInstance()
        registeredUsersRef = FirebaseDatabase.getInstance().reference.child("registeredUsersCount")
        usersRef = FirebaseDatabase.getInstance().reference.child("users")

        // Read the current count of registered users from the database
        registeredUsersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    totalRegisteredUsers = dataSnapshot.getValue(Int::class.java) ?: 0
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "Failed to load user count", Toast.LENGTH_SHORT).show()
            }
        })

        // Set a click listener for the sign-up button
        binding.button.setOnClickListener {
            // Retrieve user input (username, email, password, etc.)
            val username = binding.usernameEt.text.toString()  // New username field
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            // Validate if a radio button is selected
            val selectedOption = binding.radioGroup.checkedRadioButtonId
            val userType: String = if (selectedOption == R.id.radioSeeker) {
                "Seeker"
            } else if (selectedOption == R.id.radioCompany) {
                "Company"
            } else {
                Toast.makeText(this, "Please select a user type", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Validate input fields
            if (username.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    // Create user account using Firebase Authentication
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            totalRegisteredUsers++

                            // Store the updated count in the database
                            registeredUsersRef.setValue(totalRegisteredUsers)

                            // Store user data in the database
                            val userId = firebaseAuth.currentUser?.uid ?: ""
                            val userMap = hashMapOf<String, Any>(
                                "username" to username,  // Store username
                                "email" to email,
                                "userType" to userType
                            )
                            usersRef.child(userId).setValue(userMap)

                            // Redirect to the SignInActivity
                            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                            startActivity(intent)
                            finish()  // Finish current activity
                        } else {
                            Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_LONG).show()
            }
        }
    }
}

