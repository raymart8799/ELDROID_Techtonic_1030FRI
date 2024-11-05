package com.example.techtonic.Class


data class Reports(
    val hazardType: String,
    val location: String,
    val status: String,
    val imageResource: Int, // Can keep this if you're using local images as well
    val description: String,
    val imageUrl: String // Add this for the URL
)
