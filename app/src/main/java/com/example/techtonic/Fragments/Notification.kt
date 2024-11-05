package com.example.techtonic.Fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.techtonic.Activity.HazardReport
import com.example.techtonic.R

class Notification : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        // Trigger a notification as an example
        // You can customize when this is called based on your app logic
        showNotification("New Hazard Report", "Check your recent hazard reports!")

        return view
    }

    private fun showNotification(title: String, message: String) {
        val notificationId = 1 // Unique ID for the notification
        val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)

        // Create an intent to open the HazardReport activity when the notification is clicked
        val intent = Intent(requireContext(), HazardReport::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Build the notification
        val notification = NotificationCompat.Builder(requireContext(), "hazard_report_channel")
            .setSmallIcon(R.drawable.ic_notification) // Replace with your actual notification icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Dismiss the notification when clicked
            .build()

        // Check for notification channel and create it if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Hazard Report Notifications"
            val channelDescription = "Notifications for Hazard Reports"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel("hazard_report_channel", channelName, importance).apply {
                description = channelDescription
            }

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notification)
    }
}
