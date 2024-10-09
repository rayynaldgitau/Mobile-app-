package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JobsCategoryBy3 : AppCompatActivity() {

    private val dbref = FirebaseDatabase.getInstance().getReference("Jobs")
    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobList: ArrayList<company>
    private lateinit var companyAdapter: CompanyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_categoryby3)

        setupRecyclerView()
        setupNavbar()

        getJobData()
    }

    private fun setupRecyclerView() {
        jobRecyclerView = findViewById(R.id.rvJob)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)

        jobList = arrayListOf()
        companyAdapter = CompanyAdapter(jobList)
        jobRecyclerView.adapter = companyAdapter
    }

    private fun getJobData() {
        dbref.orderByChild("ccategory")
            .equalTo("Financial")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.d("JobsData", "Data exists: ${snapshot.childrenCount}")
                        for (jobSnapshot in snapshot.children) {
                            val job = jobSnapshot.getValue(company::class.java)
                            job?.let {
                                Log.d("JobsData", "Job: ${it.Ctitle}")
                                jobList.add(it)
                                companyAdapter.notifyItemInserted(jobList.size - 1)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }

    private fun setupNavbar() {
        val imageButton = findViewById<ImageButton>(R.id.vProfile)

        imageButton.setOnClickListener {
            val intent = Intent(this,SeekerProfile::class.java)
            startActivity(intent)
        }

        val home = findViewById<ImageButton>(R.id.vhome)
        home.setOnClickListener {
            val intent = Intent(this,JobCategory::class.java)
            startActivity(intent)
        }

        val inq = findViewById<ImageButton>(R.id.vInqury)
        inq.setOnClickListener {
            val intent = Intent(this,ActivityInsertion::class.java)
            startActivity(intent)
        }

        val category = findViewById<ImageButton>(R.id.vCategory)
        category.setOnClickListener {
            val intent = Intent(this,AllJobs::class.java)
            startActivity(intent)
        }
    }
}
