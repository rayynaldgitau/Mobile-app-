package com.example.trials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class AdminDash : AppCompatActivity() {


    private lateinit var btnJ: CardView
    private lateinit var btnInq: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dash)


        btnJ = findViewById(R.id.job)
        btnInq = findViewById(R.id.btnInq)



        btnJ.setOnClickListener{

            val intent = Intent(this,CountActivity::class.java)
            startActivity(intent)

        }

        btnInq.setOnClickListener{

            val intent = Intent(this,AdminInqView::class.java)
            startActivity(intent)
        }
    }
}