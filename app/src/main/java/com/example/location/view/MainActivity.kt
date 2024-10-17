package com.example.location.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.location.R
import com.example.location.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var actionDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        var navController=findNavController(R.id.fragmentContainerView)

        binding.bottomBar.setupWithNavController(navController)

        binding.drawerNav.setupWithNavController(navController)

        actionDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,
            R.string.nav_open ,
            R.string.nav_close
        )
        actionDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.drawerNav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout ->{
                   Firebase.auth.signOut()

                    startActivity(Intent(this, LoginActivity::class.java))
                     finish()
                }

                else -> {
                    true
                }
            }
            true
        }
        binding.drawerNav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout ->{
                    Firebase.auth.signOut()

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()

                }

                else -> {
                    true
                }
            }
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionDrawerToggle.onOptionsItemSelected(item)){
            true
        }
        else super.onOptionsItemSelected(item)
    }
}