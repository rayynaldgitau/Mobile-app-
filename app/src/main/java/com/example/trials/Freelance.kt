package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Freelance: AppCompatActivity() {
    lateinit var btnGetstart : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freelance)
        supportActionBar?.hide()

        btnGetstart = findViewById(R.id.btnGetstart)

        btnGetstart.setOnClickListener{

            val intent = Intent(this,free_page1::class.java)
            startActivity(intent)
        }
    }
}