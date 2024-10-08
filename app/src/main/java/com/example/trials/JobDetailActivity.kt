package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class JobDetailActivity : AppCompatActivity() {

    private lateinit var tvJobTitle: TextView
    private lateinit var tvCompanyName: TextView
    private lateinit var tvJobType: TextView
    private lateinit var tvJobCategory: TextView
    private lateinit var tvJobSalary: TextView
    private lateinit var tvJobDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        val applyButton = findViewById<Button>(R.id.applybtn)
        applyButton.setOnClickListener {
            goToApplyJobActivity()
        }

        // Initialize views
        tvJobTitle = findViewById(R.id.tvJobTitle)
        tvCompanyName = findViewById(R.id.tvCompanyName)
        tvJobType = findViewById(R.id.tvJobType)
        tvJobCategory = findViewById(R.id.tvJobCategory)
        tvJobSalary = findViewById(R.id.tvJobSalary)
        tvJobDescription = findViewById(R.id.tvJobDescription)

        // Get the job details from the intent
        val jobTitle = intent.getStringExtra("Ctitle")
        val companyName = intent.getStringExtra("CcompanyName")
        val jobType = intent.getStringExtra("Ctype")
        val jobCategory = intent.getStringExtra("Ccategory")
        val jobSalary = intent.getStringExtra("Csalary")
        val jobDescription = intent.getStringExtra("Cdescription")

        // Set the values to the views
        tvJobTitle.text = jobTitle
        tvCompanyName.text = companyName
        tvJobType.text = jobType
        tvJobCategory.text = jobCategory
        tvJobSalary.text = jobSalary
        tvJobDescription.text = jobDescription
    }
    private fun goToApplyJobActivity() {
        val intent = Intent(this, ApplyJobActivity::class.java)
        startActivity(intent)
    }
}
