package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FreePage1: AppCompatActivity() {
    private lateinit var btnNext1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_free_page1)
        supportActionBar?.hide()

        btnNext1 = findViewById(R.id.btnNext1)

        btnNext1.setOnClickListener{

            val intent = Intent(this,FreePage2::class.java)
            startActivity(intent)
        }
    }
}