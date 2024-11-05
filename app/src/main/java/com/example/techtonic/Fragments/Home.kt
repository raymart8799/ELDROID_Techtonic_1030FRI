package com.example.techtonic.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.techtonic.Activity.HazardReport
import com.example.techtonic.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : Fragment() {

    private lateinit var btnadd: ImageButton
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        btnadd = view.findViewById(R.id.addreport)


        val intent = Intent(requireContext(), HazardReport::class.java)


        btnadd.setOnClickListener {
            startActivity(intent)
        }


        return view
    }
}
//
////
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.Geocoder
//import android.location.Location
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.SearchView
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.fragment.app.Fragment
//import com.example.techtonic.R
//import com.example.techtonic.Activity.HazardReport
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import java.io.IOException
//
//class Home : Fragment(), OnMapReadyCallback {
//
//    private var mMap: GoogleMap? = null
//    private var currentLocation: Location? = null // Made nullable to check for null
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    private val permission = 101
//    private lateinit var btnAdd: ImageButton
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_home, container, false)
//
//        // Set up "Add Report" button
//        val btnAddReport = view.findViewById<ImageButton>(R.id.addreport)
//        btnAddReport.setOnClickListener {
//            startActivity(Intent(requireContext(), HazardReport::class.java))
//        }
//
//        // Initialize location provider client
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//
//        // Get the current location of the user
//        getCurrentLocationUser()
//
//        // Initialize SupportMapFragment and set up async map loading
//        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
//        mapFragment?.getMapAsync(this)
//
//        // Button to add a hazard report
//        btnAdd = view.findViewById(R.id.addreport)
//        btnAdd.setOnClickListener {
//            startActivity(Intent(requireContext(), HazardReport::class.java))
//        }
//
//        // Set up the search view for location search
//        val searchView = view.findViewById<SearchView>(R.id.searchLocation)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (!query.isNullOrEmpty()) {
//                    searchLocation(query)
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean = false
//        })
//
//        return view
//    }
//
//    private fun getCurrentLocationUser() {
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permission)
//            return
//        }
//
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
//            if (location != null) {
//                currentLocation = location
//                Toast.makeText(requireContext(), "${currentLocation?.latitude}, ${currentLocation?.longitude}", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(requireContext(), "Unable to fetch location", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//            Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun searchLocation(query: String) {
//        val geocoder = Geocoder(requireContext())
//        try {
//            val addressList = geocoder.getFromLocationName(query, 1)
//
//            if (!addressList.isNullOrEmpty()) {
//                val address = addressList[0]
//                val latLng = LatLng(address.latitude, address.longitude)
//                mMap?.clear() // Clear existing markers
//                mMap?.addMarker(MarkerOptions().position(latLng).title(query))
//                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
//                Toast.makeText(requireContext(), "Location found: ${address.locality}", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//            Toast.makeText(requireContext(), "Error fetching location", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if (requestCode == permission && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getCurrentLocationUser()
//        } else {
//            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        currentLocation?.let {
//            val latLng = LatLng(it.latitude, it.longitude)
//            val markerOptions = MarkerOptions().position(latLng).title("Current Location")
//
//            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//            mMap?.addMarker(markerOptions)
//        } ?: run {
//            Toast.makeText(requireContext(), "Current location is not available", Toast.LENGTH_SHORT).show()
//        }
//    }
//}

//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.Geocoder
//import android.location.Location
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.SearchView
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.fragment.app.Fragment
//import com.example.techtonic.R
//import com.example.techtonic.Activity.HazardReport
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import java.io.IOException
//
//class Home : Fragment(), OnMapReadyCallback {
//
//    private var mMap: GoogleMap? = null
//    private var currentLocation: Location? = null
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    private val permissionRequestCode = 101
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_home, container, false)
//
//        // Initialize the Add Report button
//        val btnAddReport = view.findViewById<ImageButton>(R.id.addreport)
//        btnAddReport.setOnClickListener {
//            startActivity(Intent(requireContext(), HazardReport::class.java))
//        }
//
//        // Initialize location provider client
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//
//        // Request current location
//        getCurrentLocationUser()
//
//        // Initialize the SupportMapFragment
//        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
//        mapFragment?.getMapAsync(this)
//
//        // Set up the SearchView
//        val searchView = view.findViewById<SearchView>(R.id.searchLocation)
//        searchView.setOnQueryTextListener(object : SearchView.  OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (!query.isNullOrEmpty()) {
//                    searchLocation(query)
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean = false
//        })
//
//        return view
//    }
//
//    private fun getCurrentLocationUser() {
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionRequestCode)
//            return
//        }
//
//        // Fetch the last known location
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
//            if (isAdded && location != null) { // Ensure the fragment is attached
//                currentLocation = location
//                Toast.makeText(requireContext(), "${currentLocation?.latitude}, ${currentLocation?.longitude}", Toast.LENGTH_LONG).show()
//                updateMapWithCurrentLocation() // Call a separate method to update the map
//            } else if (!isAdded) {
//                // Log the issue or handle it if needed
//            } else {
//                Toast.makeText(requireContext(), "Unable to fetch location", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//            if (isAdded) {
//                Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun searchLocation(query: String) {
//        val geocoder = Geocoder(requireContext())
//        try {
//            val addressList = geocoder.getFromLocationName(query, 1)
//            if (!addressList.isNullOrEmpty()) {
//                val address = addressList[0]
//                val latLng = LatLng(address.latitude, address.longitude)
//                mMap?.clear() // Clear any existing markers
//                mMap?.addMarker(MarkerOptions().position(latLng).title(query))
//                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
//                Toast.makeText(requireContext(), "Location found: ${address.locality}", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//            Toast.makeText(requireContext(), "Error fetching location", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if (requestCode == permissionRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getCurrentLocationUser()
//        } else {
//            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        if (currentLocation != null) {
//            val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
//            val markerOptions = MarkerOptions().position(latLng).title("Current Location")
//            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//            mMap?.addMarker(markerOptions)
//        } else {
//            Toast.makeText(requireContext(), "Current location is not available", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun updateMapWithCurrentLocation() {
//        currentLocation?.let {
//            val latLng = LatLng(it.latitude, it.longitude)
//            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//            mMap?.addMarker(MarkerOptions().position(latLng).title("Current Location"))
//        }
//    }
//}
