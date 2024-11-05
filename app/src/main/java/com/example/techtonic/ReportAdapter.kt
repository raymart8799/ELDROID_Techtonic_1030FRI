package com.example.techtonic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.example.techtonic.Activity.ReportDetailActivity
import com.example.techtonic.Class.Reports

//class ReportAdapter(private val reports: List<ReportItem>) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report2, parent, false)
//        return ReportViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
//        val report = reports[position]
//        holder.reportTitle.text = report.title
//        holder.reportStatus.text = report.status
//        holder.reportImage.setImageResource(report.imageResource)
//
//        holder.viewDetailsButton.setOnClickListener {
//            Log.d("ReportAdapter", "View Details clicked for item: ${report.title}")
//            // Handle view details click
//        }
//    }
//
//    override fun getItemCount() = reports.size
//
//    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val reportImage: ImageView = itemView.findViewById(R.id.report_image)
//        val reportTitle: TextView = itemView.findViewById(R.id.report_title)
//        val reportStatus: TextView = itemView.findViewById(R.id.report_status)
//        val viewDetailsButton: Button = itemView.findViewById(R.id.view_details_button)
//    }
//}
//
//data class ReportItem(val title: String, val status: String, val imageResource: Int)


//class ReportAdapter(context: Context, reports: List<Reports>) :
//    ArrayAdapter<Reports>(context, 0, reports) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_report2, parent, false)
//
//        val report = getItem(position)
//        val imageView = view.findViewById<ImageView>(R.id.report_image)
//        val hazardTypeTextView = view.findViewById<TextView>(R.id.report_title)
//        val statusTextView = view.findViewById<TextView>(R.id.report_status)
//        val viewDetailsButton = view.findViewById<Button>(R.id.view_details_button)
//
//        report?.let {
//            hazardTypeTextView.text = it.hazardType
//            statusTextView.text = it.status
//            imageView.setImageResource(it.imageResource)
//        }
//        viewDetailsButton.setOnClickListener {
//            // Create intent to start ReportDetailActivity
//            val intent = Intent(context, ReportDetailActivity::class.java)
//            intent.putExtra("hazardType", report.hazardType)
//            intent.putExtra("status", report.status)
//            intent.putExtra("description", "Detailed description of the hazard.") // Placeholder, add actual description if available
//            intent.putExtra("imageResId", report.imageResId)
//
//            // Start activity
//            context.startActivity(intent)
//        }
//
//        return view
//    }
//}
// ReportAdapter.kt

//class ReportAdapter(
//    private val context: Context,
//    private val reportList: List<Reports>
//) : ArrayAdapter<Reports>(context, 0, reportList) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_report2, parent, false)
//
//        val report = reportList[position]
//
//        // Set up views with report data
//        val imageView = view.findViewById<ImageView>(R.id.report_image)
//        val titleView = view.findViewById<TextView>(R.id.report_title)
//        val statusView = view.findViewById<TextView>(R.id.report_status)
//        val viewDetailsButton = view.findViewById<Button>(R.id.view_details_button)
//
//        imageView.setImageResource(report.imageResource)
//        titleView.text = report.hazardType
//        statusView.text = report.status
//
//        viewDetailsButton.setOnClickListener {
//            // Create intent to start ReportDetailActivity
//            val intent = Intent(context, ReportDetailActivity::class.java)
//            intent.putExtra("hazardType", report.hazardType)
//            intent.putExtra("status", report.status)
//            intent.putExtra("description", report.description) // Placeholder, add actual description if available
//            intent.putExtra("imageResId", report.imageResource)
//
//            // Start activity
//            context.startActivity(intent)
//        }
//
//        return view
//    }
//}

import com.bumptech.glide.Glide

class ReportAdapter(
    private val context: Context,
    private val reportList: List<Reports>
) : ArrayAdapter<Reports>(context, 0, reportList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_report2, parent, false)

        val report = reportList[position]

        val imageView = view.findViewById<ImageView>(R.id.report_image)
        val titleView = view.findViewById<TextView>(R.id.report_title)
        val statusView = view.findViewById<TextView>(R.id.report_status)
        val viewDetailsButton = view.findViewById<Button>(R.id.view_details_button)

        // Load image from URL using Glide
        Glide.with(context)
        .load(report.imageUrl) // Load the image URL
        .into(imageView)

        titleView.text = report.hazardType
        statusView.text = report.status

        viewDetailsButton.setOnClickListener {
            val intent = Intent(context, ReportDetailActivity::class.java)
            intent.putExtra("hazardType", report.hazardType)
            intent.putExtra("status", report.status)
            intent.putExtra("location", report.location)
            intent.putExtra("description", report.description)
            intent.putExtra("imageUrl", report.imageUrl)

            context.startActivity(intent)
        }

        return view
    }
}


