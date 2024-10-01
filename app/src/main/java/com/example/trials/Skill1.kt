package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Skill1: AppCompatActivity() {
    private lateinit var btnGetstart1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill1)
        supportActionBar?.hide()

        btnGetstart1 = findViewById(R.id.btninter)

        btnGetstart1.setOnClickListener{

            val intent = Intent(this,Interview1::class.java)
            startActivity(intent)
        }
    }
}