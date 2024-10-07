package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JobList : AppCompatActivity() {

    private var dbref = FirebaseDatabase.getInstance().getReference("Jobs")
    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobArrayList: ArrayList<company>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)

        jobRecyclerView = findViewById(R.id.tvCompany)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)
        jobArrayList = arrayListOf<company>()

        // Set up the apply button
        val applyButton = findViewById<Button>(R.id.applybtn)
        applyButton.setOnClickListener {
            goToApplyJobActivity()
        }

        getJobData()
    }

    private fun getJobData() {
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (jobSnapshot in snapshot.children) {
                        val job = jobSnapshot.getValue(company::class.java)
                        jobArrayList.add(job!!)
                    }
                    val adapter = CompanyAdapter(jobArrayList)
                    jobRecyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Do nothing for now
            }
        })
    }

    private fun goToApplyJobActivity() {
        val intent = Intent(this, ApplyJobActivity::class.java)
        startActivity(intent)
    }
}

