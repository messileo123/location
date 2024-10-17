package com.example.location.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.location.R
import com.example.location.databinding.ActivityMainBinding
import com.example.location.databinding.ActivityRegisterBinding
import com.example.location.view.viewmodel.AuthenticationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.*
import com.example.location.view.viewmodel.AuthenticationViewModel as AuthenticationViewModel1

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        binding.registerBtn.setOnClickListener {
            val name = binding.displayNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.conPasswordEt.text.toString()


            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this , "please enter all fields" , Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this , "passwords do not match" , Toast.LENGTH_SHORT).show()
            }
            else if (password.length<6){
                Toast.makeText(this , "please enter valid password" , Toast.LENGTH_SHORT).show()
            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this , "please enter valid email" , Toast.LENGTH_SHORT).show()
            }else{
            authenticationViewModel.register(email , password , {

                firestoreView
                startActivity(Intent(this , MainActivity::class.java))

                finish()
            } , {

                Toast.makeText(this , it, Toast.LENGTH_SHORT).show()
            })

            }
        }
    }

          override fun onStart(){
            super.onStart()
            if(Firebase.auth.currentUser!=null){
                startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                finish()
            }
        }

}