package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
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

class AllJobs : AppCompatActivity(){


    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var searchBar: EditText
    private val comList = ArrayList<company>()
    private val filteredList = ArrayList<company>() // Filtered list for search results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_jobs)

        jobRecyclerView = findViewById(R.id.rvJobs)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        searchBar = findViewById(R.id.search) // Reference to the search bar

        jobRecyclerView.layoutManager = LinearLayoutManager(this)

        // Add TextWatcher to search bar for real-time filtering
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterJobsByTitle(s.toString()) // Filter jobs as the user types
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        fetchJobData() // Fetch jobs from Firebase



        val home = findViewById<ImageButton>(R.id.vhome)
        home.setOnClickListener {
            val intent = Intent(this,JobCategory::class.java)
            startActivity(intent)
        }
        val category = findViewById<ImageButton>(R.id.vCategory)
        category.setOnClickListener {
            val intent = Intent(this,AllJobs::class.java)
            startActivity(intent)
        }
        val inq = findViewById<ImageButton>(R.id.vInqury)
        inq.setOnClickListener {
            val intent = Intent(this,ActivityInsertion::class.java)
            startActivity(intent)
        }
        val imageButton = findViewById<ImageButton>(R.id.vProfile)

        imageButton.setOnClickListener {
            val intent = Intent(this,SeekerProfile::class.java)
            startActivity(intent)
        }
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
                    updateRecyclerView(comList) // Initial update with full list
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
    private fun updateRecyclerView(list: List<company>) {
        val mAdapter = JobsAdapter(list)
        jobRecyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : JobsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@AllJobs, UpdateDeleteView::class.java).apply {
                    putExtra("CcompanyName", list[position].CcompanyName)
                    putExtra("Ctype", list[position].Ctype)
                    putExtra("Ccategory", list[position].Ccategory)
                    putExtra("Csalary", list[position].Csalary)
                    putExtra("Ctitle", list[position].Ctitle)
                    putExtra("Cdescription", list[position].Cdescription)
                }
                startActivity(intent)
            }
            override fun onMoreInfoClick(position: Int) {
                val intent = Intent(this@AllJobs, JobDetailActivity::class.java).apply {
                    putExtra("CcompanyName", list[position].CcompanyName)
                    putExtra("Ctype", list[position].Ctype)
                    putExtra("Ccategory", list[position].Ccategory)
                    putExtra("Csalary", list[position].Csalary)
                    putExtra("Ctitle", list[position].Ctitle)
                    putExtra("Cdescription", list[position].Cdescription)
                }
                startActivity(intent)
            }
        })
    }

    // Method to filter jobs by title
    private fun filterJobsByTitle(query: String) {
        filteredList.clear()

        // Filter by job title (Ctitle)
        for (job in comList) {
            if (job.Ctitle?.contains(query, ignoreCase = true) == true) {
                filteredList.add(job)
            }
        }

        // Update RecyclerView with filtered results
        updateRecyclerView(filteredList)
    }


}
