package com.example.trials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage

class SeekerDetails : AppCompatActivity() {
    lateinit var seekername: TextView
    lateinit var seekerjob: TextView
    lateinit var age: TextView
    lateinit var email: TextView
    lateinit var buttonAccept: Button
    lateinit var buttonReject: Button
    lateinit var downloadCvButton: Button // Declare the download CV button
    private lateinit var cvUrl: String // Variable to hold the CV URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_details)

        initView()
        setValuesToViews()
        setButtonListeners()
    }

    fun initView() {
        seekername = findViewById(R.id.seekername)
        seekerjob = findViewById(R.id.seekerjob)
        age = findViewById(R.id.age)
        email = findViewById(R.id.email)
        buttonAccept = findViewById(R.id.Acceptbutton)
        buttonReject = findViewById(R.id.Rejectbutton)
        downloadCvButton = findViewById(R.id.downloadCvButton) // Initialize the download button
    }

    fun setValuesToViews() {
        seekername.text = intent.getStringExtra("fullName")
        seekerjob.text = intent.getStringExtra("jobName")
        age.text = intent.getStringExtra("address")
        email.text = intent.getStringExtra("email")
        cvUrl = intent.getStringExtra("cvUrl") ?: "" // Fetch the CV URL from the intent
    }

    private fun setButtonListeners() {
        buttonAccept.setOnClickListener {
            sendEmailNotification(seekername.text.toString(), true)
        }

        buttonReject.setOnClickListener {
            sendEmailNotification(seekername.text.toString(), false)
        }

        downloadCvButton.setOnClickListener {
            if (cvUrl.isNotEmpty()) {
                downloadCv(cvUrl) // Call the function to download the CV
            } else {
                Toast.makeText(this, "CV URL is not available.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmailNotification(seekerName: String, isAccepted: Boolean) {
        val subject: String
        val message: String
        val recipientEmail = intent.getStringExtra("email") // Retrieve the email directly

        if (isAccepted) {
            subject = "Job Offer Acceptance"
            message = "Congratulations $seekerName! You have been accepted for the job offer."
        } else {
            subject = "Job Offer Rejection"
            message = "Sorry $seekerName, but you did not make the cut for this job."
        }

        // Check if recipientEmail is not null or empty
        if (recipientEmail.isNullOrEmpty()) {
            Toast.makeText(this, "Recipient email is not available.", Toast.LENGTH_SHORT).show()
            return
        }

        // Intent to send an email
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        // Verify if there's an app that can handle the email intent
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(this, "No email client found.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadCv(cvUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(cvUrl) // Set the URL to the intent
        startActivity(intent) // Start the activity to view the CV
    }
}

