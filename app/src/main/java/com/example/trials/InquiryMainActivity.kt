package com.example.trials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InquiryMainActivity : AppCompatActivity() {

    private lateinit var btnInsertData: CardView

    lateinit var btnfree: CardView
    lateinit var btnI: CardView
    private lateinit var btnprofile: CardView
    private lateinit var Jobview: CardView







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        supportActionBar?.hide()

        val firebase :DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertData = findViewById(R.id.btnInsertData)

        btnfree = findViewById(R.id.btnfree)

        btnprofile=findViewById(R.id.btnprofile)
        Jobview=findViewById(R.id.Jobview)
        btnI = findViewById(R.id.btnInquiry)





        btnI.setOnClickListener{

            val intent = Intent(this,FetchingActivity::class.java)
            startActivity(intent)
        }

        btnfree.setOnClickListener{

            val intent = Intent(this,Freelance::class.java)
            startActivity(intent)
        }


        btnInsertData.setOnClickListener{

            val intent = Intent(this,ActivityInsertion::class.java)
            startActivity(intent)
        }
        btnprofile.setOnClickListener{

            val intent = Intent(this, SeekerProfile::class.java)
            startActivity(intent)

        }

        Jobview.setOnClickListener{

            val intent = Intent(this, AllJobs::class.java)
            startActivity(intent)

        }




    }




}