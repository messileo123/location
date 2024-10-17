package com.example.location.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class firebaseViewmodel:ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection=firestore.collection("users")
    fun saveUser(context: Context,userId:String,displayName:String,email:String,location:String){
        val user =
    }
}