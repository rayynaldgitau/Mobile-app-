package com.example.trials

import Job
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trials.databinding.ActivityJobFormBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class JobFormActivity : AppCompatActivity() {

    // Firebase Realtime Database reference
    private lateinit var databaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var binding: ActivityJobFormBinding
    private var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRef = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().reference

        binding.choosefile.setOnClickListener {
            // Create an intent to open the file chooser
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf" // Set the MIME type of PDF files
            startActivityForResult(intent, 1)
        }

        // Get the job data from the intent
        val companyName = intent.getStringExtra("Ccompany_name")
        val location = intent.getStringExtra("Ccategory")
        val description = intent.getStringExtra("Ctitle")
        val type = intent.getStringExtra("Csalary")

        // Update the UI with the job details
        val companyTextView = binding.jobname
        val locationTextView = binding.jobCmpny
        val descriptionTextView = binding.timejob

        companyTextView.text = companyName ?: "Company Name Not Available"
        locationTextView.text = location ?: "Location Not Available"
        descriptionTextView.text = description ?: "Description Not Available"

        binding.submitbtn.setOnClickListener {
            val jobName = binding.jobName.text.toString()
            val fullName = binding.fllnme.text.toString()
            val email = binding.emailtxt.text.toString()
            val gender = binding.checkBox1.isChecked
            val gender2 = binding.checkBox2.isChecked
            val address = binding.addresstxt.text.toString()
            val mobile = binding.mbiletxt.text.toString()

            if (fullName.isNotEmpty() && email.isNotEmpty() && (gender || gender2) && address.isNotEmpty() && mobile.isNotEmpty()) {

                // Upload the CV file if it has been chosen
                if (fileUri != null) {
                    val fileRef = storageRef.child("jobForms/${fileUri!!.lastPathSegment}")
                    val uploadTask = fileRef.putFile(fileUri!!)
                    uploadTask.addOnSuccessListener {
                        fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            val jobs = Job(
                                fullName,
                                email,
                                gender,
                                gender2,
                                address,
                                mobile,
                                jobName,
                                downloadUri.toString()
                            )

                            val jobFormRef = databaseRef.child("jobForms").push()
                            jobFormRef.setValue(jobs).addOnSuccessListener {
                                // Show success message
                                Toast.makeText(
                                    this,
                                    "Data inserted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Clear the form fields
                                clearFormFields()
                            }.addOnFailureListener {
                                // Show failure message
                                Toast.makeText(this, "Data insertion failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }.addOnFailureListener {
                        // Show failure message
                        Toast.makeText(this, "File upload failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // If no CV file has been chosen, create a Job object without the download URL
                    val jobs = Job(fullName, email, gender, gender2, address, mobile, jobName)

                    val jobFormRef = databaseRef.child("jobForms").push()
                    jobFormRef.setValue(jobs).addOnSuccessListener {
                        // Show success message
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()
                        clearFormFields()
                    }
                }
            }
        }
    }

    private fun clearFormFields() {
        binding.fllnme.setText("")
        binding.emailtxt.setText("")
        binding.checkBox1.isChecked = false
        binding.checkBox2.isChecked = false
        binding.addresstxt.setText("")
        binding.mbiletxt.setText("")
        binding.jobName.setText("")
        fileUri = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            fileUri = data.data // Get the Uri of the selected file
            // Optionally, you can display the selected file's name or URI in your UI
            Toast.makeText(this, "File selected: $fileUri", Toast.LENGTH_SHORT).show()
        }
    }
}
