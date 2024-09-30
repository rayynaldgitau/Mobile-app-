package com.example.trials

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FreePage2: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_free_page2)
        supportActionBar?.hide()
    }
}