package com.example.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.location.databinding.ItemUserBinding


class UserAdapter(private var userList: List<com.example.location.model.User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: com.example.location.model.User) {
            binding.apply {
                displayNameTxt.text = user.displayName
                emailTxt.text = user.email
                locationTxt.text = user.location
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }
    fun updateData(newList: List<com.example.location.model.User>) {
        userList = newList
        notifyDataSetChanged()
    }
}








