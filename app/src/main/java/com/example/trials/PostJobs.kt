package com.example.trials

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostJobs : AppCompatActivity() {


    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var comList: ArrayList<CompanyData>
    private lateinit var dbRef: DatabaseReference

    private lateinit var vProfile: ImageButton
    private lateinit var vCategory: ImageButton
    private lateinit var vInqury: ImageButton
    private lateinit var vhome: ImageButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_jobs)

        jobRecyclerView = findViewById(R.id.rvJob)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        vProfile= findViewById(R.id.vProfile )
        vCategory=findViewById(R.id.vCategory)
        vInqury= findViewById(R.id.vInqury)
        vhome=findViewById(R.id.vhome)
        comList = arrayListOf<CompanyData>()



        getJobData()


        vProfile.setOnClickListener {
            val intent = Intent(this,CompanyDash::class.java)
            startActivity(intent)
        }
        vCategory.setOnClickListener {
            val intent = Intent(this,JobCategory::class.java)
            startActivity(intent)
        }


        vInqury.setOnClickListener {
            val intent = Intent(this,ActivityInsertion::class.java)
            startActivity(intent)
        }

        vhome.setOnClickListener {
            val intent = Intent(this, InquiryMainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun getJobData(){

        jobRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Jobs")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                comList.clear()
                if (snapshot.exists()){
                    for (comSnap in snapshot.children){
                        val comData = comSnap.getValue(CompanyData::class.java)
                        comList.add(comData!!)

                    }


                    val mAdapter = JobsAdapter(comList)
                    jobRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : JobsAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@PostJobs, UpdateDeleteView::class.java)

                            //put extras
                            intent.putExtra("CcomId", comList[position].CcomId)
                            intent.putExtra("CcompanyName", comList[position].CcompanyName)
                            intent.putExtra("Ctype", comList[position].Ctype)
                            intent.putExtra("Ccategory", comList[position].Ccategory)
                            intent.putExtra("Csalary", comList[position].Csalary)
                            intent.putExtra("Ctitle", comList[position].Ctitle)
                            intent.putExtra("Cdescription", comList[position].Cdescription)
                            startActivity(intent)
                        }

                    })

                    jobRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }



        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, CompanyDash::class.java)
        startActivity(intent)
        finish()
    }


}
