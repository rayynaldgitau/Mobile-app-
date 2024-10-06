package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import Job
import android.util.Log
import android.widget.Toast

class RecieveJobApplication : AppCompatActivity() {

    private lateinit var SekRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var SekList: ArrayList<Job>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recieve_job_application)

        SekRecyclerView = findViewById(R.id.rvSeeker)
        SekRecyclerView.layoutManager = LinearLayoutManager(this)
        SekRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        SekList = arrayListOf()

        getSekData()

        // Handling back button press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@RecieveJobApplication, CompanyDash::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun getSekData() {
        SekRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("jobForms")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                SekList.clear()
                if (snapshot.exists()) {
                    for (sekSnap in snapshot.children) {
                        val sekData = sekSnap.getValue(Job::class.java)
                        if (sekData != null) {
                            SekList.add(sekData)
                        }
                    }
                    val mAdapter = SeekerAdapter(SekList)
                    SekRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : SeekerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@RecieveJobApplication, SeekerDetails::class.java)
                            // put extras
                            intent.putExtra("fullName", SekList[position].fullName)
                            intent.putExtra("jobName", SekList[position].jobName)
                            intent.putExtra("email", SekList[position].email)
                            intent.putExtra("address", SekList[position].address)
                            intent.putExtra("mobile", SekList[position].mobile)
                            intent.putExtra("cvUrl", SekList[position].cvUrl)
                            startActivity(intent)
                        }
                    })

                    SekRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Show a toast message to notify the user
                Toast.makeText(this@RecieveJobApplication, "Failed to load data. Please try again.", Toast.LENGTH_SHORT).show()

                // Log the error for debugging
                Log.e("RecieveJobApplication", "Database error: ${error.message}")
            }
        })
    }
}
