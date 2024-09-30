package com.example.trials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class Interview1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview1)
        supportActionBar?.hide()
    }
}