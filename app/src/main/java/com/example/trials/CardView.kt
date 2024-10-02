package com.example.trials



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class CardView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)

        // Get a reference to the "find Another job" button
        val applyButton = findViewById<Button>(R.id.btnRejob)

        // Set a click listener for the button
        applyButton.setOnClickListener {
            // Create an intent to start the ApplyActivity
            val intent = Intent(this,JobViewActivity::class.java)
            startActivity(intent)
        }
        val imageButton = findViewById<ImageButton>(R.id.profile)

        imageButton.setOnClickListener {
            val intent = Intent(this,SeekerProfile::class.java)
            startActivity(intent)
        }

        val category = findViewById<ImageButton>(R.id.catogery)
        category.setOnClickListener {
            val intent = Intent(this, JobViewActivity::class.java)
            startActivity(intent)
        }

        val home = findViewById<ImageButton>(R.id.imageView3)
        home.setOnClickListener {
            val intent = Intent(this, InquiryMainActivity::class.java)
            startActivity(intent)
        }


        val inq= findViewById<ImageButton>(R.id.imageView6)
        inq.setOnClickListener {
            val intent = Intent(this, ActivityInsertion::class.java)
            startActivity(intent)
        }



    }
}