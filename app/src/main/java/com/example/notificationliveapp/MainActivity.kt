package com.example.notificationliveapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import com.example.notificationliveapp.databinding.ActivityMainBinding


const val CHANNEL_ID = "my_channel"


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        setupNotifications()

        binding.notificationBTN.setOnClickListener {
            //Sende notification mit Inhalt von Textfeld

            val text = binding.notificationET.text.toString()
            binding.notificationET.setText("")

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContent()
                .setContentTitle("Hallo Welt")
                .setContentText(text)
                .setSmallIcon(R.drawable.baseline_all_inclusive_24)
                .build()

            notificationManager.notify(1, notification)
//            notificationManager.notify(2, notification)
        }

    }


    fun setupNotifications() {

        requestPermission()
        createNotificationChannel()

    }

    private fun requestPermission(){
        val permissionRequestLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
                }
            }

        permissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun createNotificationChannel() {

        val channel = NotificationChannel(
            CHANNEL_ID,
            "My Notification Channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        channel.description = "Zeige Notifications"

        notificationManager.createNotificationChannel(channel)
    }

}