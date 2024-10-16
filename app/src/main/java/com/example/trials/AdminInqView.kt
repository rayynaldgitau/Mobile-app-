package com.example.trials

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trials.EmpAdapter
import com.example.trials.InquiryDetailsActivity
import com.example.trials.InquiryModel
import com.example.trials.R
import com.google.firebase.database.*

class AdminInqView : AppCompatActivity(){

    private lateinit var InqRecyclerView: RecyclerView
    private lateinit var List: ArrayList<InquiryModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)
        supportActionBar?.hide()

        InqRecyclerView = findViewById(R.id.rvEmp)
        InqRecyclerView.layoutManager = LinearLayoutManager(this)
        InqRecyclerView.setHasFixedSize(true)

        List = arrayListOf<InquiryModel>()

        getData()
    }

    private fun getData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Inquiries")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                List.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(InquiryModel::class.java)
                        List.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(List)
                    InqRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }
}