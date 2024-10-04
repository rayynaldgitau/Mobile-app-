package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JobViewActivity: AppCompatActivity() {


    private var dbref = FirebaseDatabase.getInstance().getReference("Jobs")
    private lateinit var jobRecyclerView: RecyclerView
    lateinit var jobArrayList: ArrayList<company>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_view)

        jobRecyclerView = findViewById(R.id.rvJob)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)

        jobArrayList = arrayListOf<company>()
        val companyAdapter = CompanyAdapter(jobArrayList)
        jobRecyclerView.adapter = companyAdapter

        getJobData()
    }

    fun getJobData(){
        dbref.orderByChild("ccategory").equalTo("it" ).addValueEventListener(object :
            ValueEventListener {

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

        //navbar
        val imageButton = findViewById<ImageButton>(R.id.vProfile)

        imageButton.setOnClickListener {
            val intent = Intent(this,SeekerProfile::class.java)
            startActivity(intent)
        }

        val home = findViewById<ImageButton>(R.id.vhome)
        home.setOnClickListener {
            val intent = Intent(this,ActivityInsertion::class.java)
            startActivity(intent)
        }

        val inq = findViewById<ImageButton>(R.id.vInqury)
        inq.setOnClickListener {
            val intent = Intent(this, InquiryMainActivity::class.java)
            startActivity(intent)
        }




    }
}