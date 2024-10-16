package com.example.trials

import Job
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ReadData : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var readDataBtn: Button
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retrieveapplication)

        // Get a reference to your Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()

        // Get a reference to the "jobForms" node in your database
        val jobFormsRef = database.getReference("jobForms")

        // Get references to the UI components
        etUsername = findViewById(R.id.etusername)
        readDataBtn = findViewById(R.id.readdataBtn)

        // Set an OnClickListener on the button to read data
        readDataBtn.setOnClickListener {
            // Get the username input from the user
            val fullName = etUsername.text.toString()

            // Query the jobForms node to find the first child with a matching fullName value
            val query = jobFormsRef.orderByChild("fullName").equalTo(fullName)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Get the first child (there should only be one matching child)
                        val jobFormSnapshot = snapshot.children.first()

                        // Get the job form data
                        val jobName = jobFormSnapshot.child("jobName").getValue(String::class.java)
                        val fullName =
                            jobFormSnapshot.child("fullName").getValue(String::class.java)
                        val email = jobFormSnapshot.child("email").getValue(String::class.java)
                        val gender = jobFormSnapshot.child("gender").getValue(Boolean::class.java)
                        val gender2 = jobFormSnapshot.child("gender2").getValue(Boolean::class.java)
                        val address = jobFormSnapshot.child("address").getValue(String::class.java)
                        val mobile = jobFormSnapshot.child("mobile").getValue(String::class.java)
                        val cvUrl = jobFormSnapshot.child("cvUrl").getValue(String::class.java)

                        // Create a Job object from the retrieved data
                        val job = Job(
                            fullName!!,
                            email!!, gender!!, gender2!!, address!!, mobile!!, jobName!!, cvUrl!!
                        )

                        // Pass the Job object to your details activity
                        val intent = Intent(this@ReadData,JobEditDelete::class.java)
                        intent.putExtra("job", job)
                        startActivity(intent)
                    } else {
                        // Show a message indicating that no job form was found with the provided full name
                        Toast.makeText(
                            this@ReadData,
                            "No job form found with the provided full name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Show an error message
                    Toast.makeText(
                        this@ReadData,
                        "Failed to retrieve job form: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

}

fun Intent.putExtra(s: String, job: Job) {

}
