package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeekerProfile : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_profile)

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("users")

        // Get the current user's ID
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            getUserName(userId)
        } else {
            // Handle the case when user is not logged in
            val textView = findViewById<TextView>(R.id.textView6)
            textView.text = "User not logged in"
        }

        setupNavigationButtons()
        setupSignOutButton() // Initialize sign out button
    }

    private fun setupNavigationButtons() {
        val imageButton = findViewById<ImageButton>(R.id.rcntbtn)
        imageButton.setOnClickListener {
            val intent = Intent(this, ReadData::class.java)
            startActivity(intent)
        }

        val category = findViewById<ImageButton>(R.id.catogry)
        category.setOnClickListener {
            val intent = Intent(this, AllJobs::class.java)
            startActivity(intent)
        }

        val home = findViewById<ImageButton>(R.id.imageView10)
        home.setOnClickListener {
            val intent = Intent(this, JobCategory::class.java)
            startActivity(intent)
        }

        val inq = findViewById<ImageButton>(R.id.imageView12)
        inq.setOnClickListener {
            val intent = Intent(this, InquiryDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSignOutButton() {
        val signOutButton = findViewById<Button>(R.id.signOutButton)
        signOutButton.setOnClickListener {
            signOut() // Call sign out function
        }
    }

    private fun signOut() {
        // Sign out the user from Firebase Auth
        FirebaseAuth.getInstance().signOut()

        // Navigate back to the login screen (assuming it's called LoginActivity)
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear the back stack
        startActivity(intent)
        finish() // Close the current activity
    }

    private fun getUserName(userId: String) {
        // Reference to the specific user's username in the database
        val usernameRef = database.child(userId).child("username")

        // Listener to retrieve data
        usernameRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Get the username
                    val username = snapshot.getValue(String::class.java)

                    // Find the TextView by ID and set the username
                    val textView = findViewById<TextView>(R.id.textView6)
                    textView.text = username ?: "Unknown User"
                } else {
                    // Handle the case when username does not exist
                    val textView = findViewById<TextView>(R.id.textView6)
                    textView.text = "Username not found"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
    }
}