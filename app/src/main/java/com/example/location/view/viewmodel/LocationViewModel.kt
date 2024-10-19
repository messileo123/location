package com.example.location.view.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener

class LocationViewModel:ViewModel() {



        private var fusedLocationClient: FusedLocationProviderClient? = null
        // Method to get the last known location
        fun getLastLocation(callback: (String) -> Unit) {
           fusedLocationClient?.lastLocation
                ?.addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful && it.result != null) {
                        val lastLocation = it.result
                        val latitude = lastLocation.latitude
                        val longitude = lastLocation.longitude
                        val location = "Lat: $latitude, Long: $longitude"
                        callback(location)
                    } else {
                        // Handle failure or missing permissions
                        callback("Location not available")
                    }
                })
        }
        // Method to initialize fusedLocationClient
        fun initializeFusedLocationClient(client: FusedLocationProviderClient) {
            fusedLocationClient = client
        }
    }

