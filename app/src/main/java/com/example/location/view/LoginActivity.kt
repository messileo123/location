package com.example.location.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.location.R
import com.example.location.databinding.ActivityLoginBinding
import com.example.location.view.viewmodel.AuthenticationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this , "please enter email and password" , Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this , "please enter valid email" , Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this , "please enter valid password" , Toast.LENGTH_SHORT).show()

            } else
                authenticationViewModel.login(email , password , {
                    startActivity(Intent(this , MainActivity::class.java))
                    finish()
                } , {
                    Toast.makeText(this , it , Toast.LENGTH_SHORT).show()
                })

        }
        binding.registerTxt.setOnClickListener {
            startActivity(Intent(this , RegisterActivity::class.java))
            finish()
        }

        binding.passwordEt.setOnClickListener {
            Toast.makeText(this , "forget password" , Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this@LoginActivity , MainActivity::class.java))
                finish()

        }
    }
}