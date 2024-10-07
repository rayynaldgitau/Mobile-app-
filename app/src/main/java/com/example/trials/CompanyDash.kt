package com.example.trials
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompanyDash : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_dash)

        val createJob = findViewById<Button>(R.id.create_job)
        val tvJobCount = findViewById<TextView>(R.id.tvJobCount)
        val rvJobCount = findViewById<TextView>(R.id.rvJobCount)

        val comId = intent.getStringExtra("comId")
        var dbRef = FirebaseDatabase.getInstance().getReference("Jobs")
        dbRef.orderByChild("CcomId").equalTo(comId).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val jobCount = snapshot.childrenCount.toInt()
                tvJobCount.text = "Post Jobs: $jobCount"
            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val fullName = intent.getStringExtra("fullName")
        var sdbRef = FirebaseDatabase.getInstance().getReference("jobForms")
        sdbRef.orderByChild("sfullName").equalTo(fullName).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rjobCount = snapshot.childrenCount.toInt()
                rvJobCount.text = "Recieved Job Applications: $rjobCount"
            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        createJob.setOnClickListener {
            val intent = Intent(this@CompanyDash,JobAdd::class.java)
            startActivity(intent)
            finish()
        }

        val  viewJobs = findViewById<Button>(R.id.view_jobs)

        viewJobs.setOnClickListener {
            val intent = Intent(this@CompanyDash,PostJobs::class.java)
            startActivity(intent)

        }

        val notifi = findViewById<Button>(R.id.notifi)

        notifi.setOnClickListener {
            val intent = Intent(this@CompanyDash,RecieveJobApplication::class.java)
            startActivity(intent)
            finish()
        }


        val home = findViewById<ImageButton>(R.id.vhome)
        home.setOnClickListener {
            val intent = Intent(this,InquiryMainActivity::class.java)
            startActivity(intent)
        }

        val inq = findViewById<ImageButton>(R.id.vInqury)
        inq.setOnClickListener {
            val intent = Intent(this,ActivityInsertion::class.java)
            startActivity(intent)
        }
        val vCategory=findViewById<ImageButton>(R.id.vCategory)
        vCategory.setOnClickListener {
            val intent = Intent(this,JobCategory::class.java)
            startActivity(intent)
        }

    }
}