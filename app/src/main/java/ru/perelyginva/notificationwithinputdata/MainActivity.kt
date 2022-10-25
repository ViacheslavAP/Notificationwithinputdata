package ru.perelyginva.notificationwithinputdata

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import ru.perelyginva.notificationwithinputdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val channelID = "com.example.notifications.channel1"
    private var notificationManager: NotificationManager? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "Demo", "Demo")

        binding?.btn?.setOnClickListener(View.OnClickListener {
            displayNotification()
        })
    }

    private fun displayNotification() {

        val notificationID = 5
        val startInfoNotification = Intent(this, Info::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            startInfoNotification,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val remoteInput: RemoteInput = RemoteInput.Builder(Constanst.KEY_REPLY).run {
            setLabel("Вы согласны сделать заказ?")
            build()
        }

        val replyAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                0,
                "Ответить",
                pendingIntent
            ).addRemoteInput(remoteInput)
                .build()

        val startDetailsNotification = Intent(this, Details::class.java)

        val pendingDetails: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            startDetailsNotification,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val actionDetails: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                R.drawable.ic_details, "Детали", pendingDetails
            ).build()


        val startPriceNotification = Intent(this, Price::class.java)

        val pendingPrice: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            startPriceNotification,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val actionPrice: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                R.drawable.ic_ruble, "Цена", pendingPrice
            ).build()

        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Новый заказ!")
            .setContentText("Поступил новый заказ!")
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction(actionDetails)
            .addAction(actionPrice)
            .addAction(replyAction)
            .build()

        notificationManager?.notify(notificationID, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)
        }
    }
}