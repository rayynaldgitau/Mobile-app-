package com.example.trials

import Job
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ReadData : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var readDataBtn: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retrieveapplication)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Get references to the UI components
        etUsername = findViewById(R.id.etusername)
        readDataBtn = findViewById(R.id.readdataBtn)

        // Set an OnClickListener on the button to read data
        readDataBtn.setOnClickListener {
            // Get the username input from the user
            val fullName = etUsername.text.toString()

            // Query the Firestore "jobForms" collection to find documents with a matching fullName field
            db.collection("jobForms")
                .whereEqualTo("fullName", fullName)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        // Get the first matching document
                        val jobFormDocument = querySnapshot.documents.first()

                        // Extract data from the document
                        val jobName = jobFormDocument.getString("jobName")
                        val fullName = jobFormDocument.getString("fullName")
                        val email = jobFormDocument.getString("email")
                        val gender = jobFormDocument.getBoolean("gender")
                        val gender2 = jobFormDocument.getBoolean("gender2")
                        val address = jobFormDocument.getString("address")
                        val mobile = jobFormDocument.getString("mobile")
                        val cvUrl = jobFormDocument.getString("cvUrl")

                        // Create a Job object from the retrieved data
                        val job = Job(
                            fullName!!,
                            email!!, gender!!, gender2!!, address!!, mobile!!, jobName!!, cvUrl!!
                        )

                        // Pass the Job object to your details activity
                        val intent = Intent(this@ReadData, JobEditDelete::class.java)
                        intent.putExtra("job", job)
                        startActivity(intent)
                    } else {
                        // Show a message indicating no job form was found with the provided full name
                        Toast.makeText(
                            this@ReadData,
                            "No job form found with the provided full name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { exception ->
                    // Show an error message
                    Toast.makeText(
                        this@ReadData,
                        "Failed to retrieve job form: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}

fun Intent.putExtra(s: String, job: Job) {
    // Serialize and add the Job object to the Intent here
}
