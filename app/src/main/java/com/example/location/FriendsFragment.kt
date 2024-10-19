package com.example.location.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.location.adapter.UserAdapter
import com.example.location.databinding.FragmentFriendsBinding
import com.example.location.view.viewmodel.AuthenticationViewModel // Update import to match package structure
import com.example.location.view.viewmodel.LocationViewModel // Update import to match package structure
import com.example.location.viewmodel.FirestoreViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FriendsFragment : Fragment() {

    private lateinit var binding: FragmentFriendsBinding
    private lateinit var firestoreViewModel: FirestoreViewModel
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Registering location permission launcher
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view binding
        binding = FragmentFriendsBinding.inflate(inflater, container, false)

        // Initialize ViewModels and other components
        setupViewModels()
        setupLocationClient()
        setupRecyclerView()
        checkLocationPermission()

        // Navigate to MapsActivity on button click
        binding.locationBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MapsActivity::class.java))
        }

        return binding.root
    }

    // Initialize ViewModels with ViewModelProvider
    private fun setupViewModels() {
        firestoreViewModel = ViewModelProvider(this)[FirestoreViewModel::class.java]
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
    }

    // Setup fused location client for getting location updates
    private fun setupLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModel.initializeFusedLocationClient(fusedLocationClient)
    }

    // Set up RecyclerView with adapter and layout manager
    private fun setupRecyclerView() {
        userAdapter = UserAdapter(emptyList())
        binding.userRV.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        fetchUsers()
    }

    // Fetch all users from Firestore and update the RecyclerView
    private fun fetchUsers() {
        firestoreViewModel.getAllUsers(requireContext()) { users ->
            userAdapter.updateData(users)
        }
    }

    // Check if location permission is granted, otherwise request it
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getLocation() // Permission already granted
        }
    }

    // Get the last known location and update it in Firestore
    private fun getLocation() {
        locationViewModel.getLastLocation { location ->
            authenticationViewModel.getCurrentUserId()?.let { userId ->
                firestoreViewModel.updateUserLocation(requireContext(), userId, location)
            } ?: Toast.makeText(
                requireContext(),
                "User not found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
