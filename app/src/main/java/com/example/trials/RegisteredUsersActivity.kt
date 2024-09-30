package com.example.trials

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisteredUsersActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registered_users)
        val count = intent.getIntExtra("registeredUsersCount", 0)

        val countTextView = findViewById<TextView>(R.id.countTextView)
        countTextView.text = "Registered Users: $count"


    }

}