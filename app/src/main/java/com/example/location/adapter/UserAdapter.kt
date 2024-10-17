package com.example.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.location.adapter.UserAdapter.UserViewHolder
import com.google.firebase.firestore.auth.User

class UserAdapter(private var userList: List<User>) : RecyclerView.Adapter<UserViewHolder>() {
    class UserViewHolder(binding:ItemViewHodler){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }



}