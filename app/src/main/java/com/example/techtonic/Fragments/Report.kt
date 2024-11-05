//package com.example.techtonic.Fragments
//
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ListView
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.techtonic.R
//import com.example.techtonic.ReportAdapter
//import com.example.techtonic.ReportItem
//
//
//class Report : Fragment() {
//
//    private lateinit var reportAdapter: ReportAdapter // You'll need to update this
//    private lateinit var listView: ListView // Change to ListView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout and capture the view
//        val view = inflater.inflate(R.layout.fragment_report, container, false)
//
//        try {
//            // Initialize ListView
//            listView = view.findViewById(R.id.recycler_view_reports)
//
//            // Sample data to populate the ListView
//            val reports = listOf(
//                ReportItem("Debris", "In Progress", R.drawable.debris_image),
//                ReportItem("Wires", "Pending", R.drawable.wire_image),
//                ReportItem("Pothole", "Pending", R.drawable.pothole_image),
//                ReportItem("Pothole", "In Progress", R.drawable.pothole_image)
//            )
//
//            // Set up the adapter with sample data
//            reportAdapter = ReportAdapter(requireContext(), reports) // Pass context
//            listView.adapter = reportAdapter
//
//            Log.d("ReportStatusFragment", "ListView initialized successfully.")
//        } catch (e: Exception) {
//            // Log and display error message
//            Log.e("ReportStatusFragment", "Error initializing ListView: ${e.message}")
//            Toast.makeText(requireContext(), "Error loading reports", Toast.LENGTH_SHORT).show()
//        }
//
//        return view
//    }
//}

//
package com.example.techtonic.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import com.example.techtonic.Class.Reports
import com.example.techtonic.R
import com.example.techtonic.ReportAdapter
import com.google.firebase.database.*
import java.util.Locale

class Report : Fragment() {

    private lateinit var reportAdapter: ReportAdapter
    private lateinit var listView: ListView
    private lateinit var database: DatabaseReference
    private lateinit var searchView: SearchView
    private val reports = mutableListOf<Reports>()
    private val filteredReports = mutableListOf<Reports>() // List for filtered results

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)

        listView = view.findViewById(R.id.recycler_view_reports) // Check ID here
        searchView = view.findViewById(R.id.search_bar) // Initialize searchView
        database = FirebaseDatabase.getInstance().getReference("hazardReports")

        // Use filteredReports for the adapter
        reportAdapter = ReportAdapter(requireContext(), filteredReports)
        listView.adapter = reportAdapter

        // Fetch data from Firebase
        fetchReportsFromFirebase()

        // Set up the SearchView to filter results
        setupSearchView()


        return view
    }

//    private fun fetchReportsFromFirebase() {
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                reports.clear()
//                for (data in snapshot.children) {
//                    val hazardType = data.child("hazardType").getValue(String::class.java) ?: "Unknown"
//                    val description = data.child("description").getValue(String::class.java) ?: ""
//                    val status = "Pending" // Default status; adjust if needed
//                    val imageResource = getImageResource(hazardType)
//
//                    val reportItem = Reports(hazardType, status, imageResource, description)
//                    reports.add(reportItem)
//                }
//                // Initially show all reports
//                filteredReports.clear()
//                filteredReports.addAll(reports)
//                reportAdapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("ReportFragment", "Failed to read reports from Firebase", error.toException())
//                Toast.makeText(requireContext(), "Error loading reports", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
private fun fetchReportsFromFirebase() {
    database.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            reports.clear()
            for (data in snapshot.children) {
                val hazardType = data.child("hazardType").getValue(String::class.java) ?: "Unknown"
                val description = data.child("description").getValue(String::class.java) ?: ""
                val location = data.child("location").getValue(String::class.java) ?: ""
                val status = "Pending" // Default status
                val imageUrl = data.child("imageUrl").getValue(String::class.java) ?: ""

                val reportItem = Reports(hazardType,location, status, R.drawable.pothole_image, description, imageUrl)
                reports.add(reportItem)
            }
            filteredReports.clear()
            filteredReports.addAll(reports)
            reportAdapter.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("ReportFragment", "Failed to read reports from Firebase", error.toException())
            Toast.makeText(requireContext(), "Error loading reports", Toast.LENGTH_SHORT).show()
        }
    })
}


    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterReports(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterReports(newText)
                return true
            }
        })
    }

    private fun filterReports(query: String?) {
        filteredReports.clear()
        if (!query.isNullOrEmpty()) {
            val searchQuery = query.lowercase(Locale.getDefault())
            // Filter reports based on hazard type
            filteredReports.addAll(reports.filter { it.hazardType.lowercase(Locale.getDefault()).contains(searchQuery) })
        } else {
            // If query is empty, show all reports
            filteredReports.addAll(reports)
        }
        reportAdapter.notifyDataSetChanged() // Update adapter with the filtered list
    }
}


