package com.example.trials

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostJobs2: AppCompatActivity() {


    private var dbref = FirebaseDatabase.getInstance().getReference("Jobs")
    private lateinit var jobRecyclerView: RecyclerView
    lateinit var jobArrayList: ArrayList<company>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_jobs)

        jobRecyclerView = findViewById(R.id.rvJob)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)

        jobArrayList = arrayListOf<company>()
        val companyAdapter = CompanyAdapter(jobArrayList)
        jobRecyclerView.adapter = companyAdapter


        getJobData()

    }

    fun getJobData(){
        dbref.orderByChild("ccompanyName").addValueEventListener(object:
        ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (jobSnapshot in snapshot.children){
                        val job = jobSnapshot.getValue(company::class.java)
                        jobArrayList.add(job!!)
                    }
                    jobRecyclerView.adapter?.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Do nothing for now
            }

        })
    }
}