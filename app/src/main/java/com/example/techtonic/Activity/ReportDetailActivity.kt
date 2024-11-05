// ReportDetailActivity.kt
package com.example.techtonic.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.techtonic.R
//
//class ReportDetailActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_report_detail)
//
//        val detailImage = findViewById<ImageView>(R.id.detail_image)
//        val detailTitle = findViewById<TextView>(R.id.detail_title)
//        val detailStatus = findViewById<TextView>(R.id.detail_status)
//        val detailDescription = findViewById<TextView>(R.id.detail_description)
//
//        // Retrieve data passed from the adapter
//        val hazardType = intent.getStringExtra("hazardType")
//        val status = intent.getStringExtra("status")
//        val description = intent.getStringExtra("description")
//        val imageResId = intent.getIntExtra("imageResId", R.drawable.report_icon)
//
//        // Set data to views
//        detailTitle.text = hazardType
//        detailStatus.text = status
//        detailDescription.text = description
//        detailImage.setImageResource(imageResId)
//    }
//}
// ReportDetailActivity.kt
import com.bumptech.glide.Glide

class ReportDetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_detail)

        val hazardType = intent.getStringExtra("hazardType")
        val status = intent.getStringExtra("status")
        val description = intent.getStringExtra("description")
        val location = intent.getStringExtra("location")
        val imageUrl = intent.getStringExtra("imageUrl")

        findViewById<TextView>(R.id.detail_title).text = hazardType
        findViewById<TextView>(R.id.detail_location).text = location
        findViewById<TextView>(R.id.detail_status).text = status
        findViewById<TextView>(R.id.detail_description).text = description

        val imageView = findViewById<ImageView>(R.id.detail_image)
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.pothole_image) // Placeholder image
            .into(imageView)
    }
}
