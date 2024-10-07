package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PostJobs : AppCompatActivity() {

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private val comList = ArrayList<company>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_jobs)

        jobRecyclerView = findViewById(R.id.rvJob)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        fetchJobData()
    }

    // Fetch job data using coroutines
    private fun fetchJobData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Show loading state on the main thread
                withContext(Dispatchers.Main) {
                    jobRecyclerView.visibility = View.GONE
                    tvLoadingData.visibility = View.VISIBLE
                }

                // Fetch data from Firebase
                val snapshot = FirebaseDatabase.getInstance()
                    .getReference("Jobs")
                    .get()
                    .await()

                comList.clear()

                if (snapshot.exists()) {
                    for (comSnap in snapshot.children) {
                        val comData = comSnap.getValue<company>()
                        comData?.let { comList.add(it) }
                    }
                }

                // Update the RecyclerView on the main thread
                withContext(Dispatchers.Main) {
                    updateRecyclerView()
                }

            } catch (e: Exception) {
                Log.e("FirebaseError", "Error retrieving data", e)
            } finally {
                // Hide loading state on the main thread
                withContext(Dispatchers.Main) {
                    jobRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }
        }
    }

    // Separate method to update the RecyclerView
    private fun updateRecyclerView() {
        val mAdapter = JobsAdapter(comList)
        jobRecyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : JobsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@PostJobs, UpdateDeleteView::class.java).apply {

                    putExtra("CcompanyName", comList[position].CcompanyName)
                    putExtra("Ctype", comList[position].Ctype)
                    putExtra("Ccategory", comList[position].Ccategory)
                    putExtra("Csalary", comList[position].Csalary)
                    putExtra("Ctitle", comList[position].Ctitle)
                    putExtra("Cdescription", comList[position].Cdescription)
                }
                startActivity(intent)
            }
        })
    }
}

