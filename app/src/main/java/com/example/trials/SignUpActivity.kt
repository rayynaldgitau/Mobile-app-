package com.example.trials

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trials.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivitySignUpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.logInLink.setOnClickListener{
            val intent = Intent(this , SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmpass = binding.confirmpass.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty() && confirmpass.isNotEmpty()){
                if (password == confirmpass){


                    firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this , SignInActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this , it.exception.toString() , Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this , "Password isn't matching" , Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this , "Empty fields are not allowed" , Toast.LENGTH_SHORT).show()
            }

        }

    }
}