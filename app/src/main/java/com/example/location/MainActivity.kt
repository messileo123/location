package com.example.location

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    lateinit var actionDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var navController=findNavController(R.id.fragmentContainerView)
        binding.bottomBar.setupWithNavController(navController)
        actionDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.nav_open,R.string.nav_close)
        actionDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}