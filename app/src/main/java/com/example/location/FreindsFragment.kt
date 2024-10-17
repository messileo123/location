package com.example.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.location.model.User
import com.example.location.view.RegisterActivity
import com.example.location.view.viewmodel.AuthenticationViewModel
import com.example.location.view.viewmodel.FirestoreViewModel
import com.google.firebase.firestore.FirebaseFirestore

class FriendsFragment : Fragment() {
    private lateinit var binding: FriendsFragment
    private lateinit var firestoreViewModel: FirestoreViewModel
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        RegisterActivity(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FriendsFragment.inflate(inflater,container, false)

        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModel.initializeFusedLocationClient(fusedLocationClient)


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {

            getLocation()
        }
        userAdapter = UserAdapter(emptyList())
        binding.userRV.apply {

            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        fetchUsers()

        binding.locationBtn.setOnClickListener {

        }


        return binding.root
    }

    private fun fetchUsers() {
        firestoreViewModel.getAllUsers(requireContext()){
            userAdapter.updateData(it)
        }
    }

    private fun getLocation() {
        locationViewModel.getLastLocation {
            // Save location to Firestore for the current user
            authenticationViewModel.getCurrentUserId().let { userId ->
                firestoreViewModel.updateUserLocation(requireContext(),userId, it)
            }
        }
    }


}
