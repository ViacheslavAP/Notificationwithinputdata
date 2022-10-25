package ru.perelyginva.notificationwithinputdata

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.getSystemService
import ru.perelyginva.notificationwithinputdata.databinding.ActivityInfoBinding

class Info : AppCompatActivity() {

    private var binding: ActivityInfoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        receiveInput()
    }

    private fun receiveInput() {

        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null){
            val inputString = remoteInput.getCharSequence(Constanst.KEY_REPLY).toString()
            binding?.textInfo?.text = inputString

            val notificationID = 7
             val channelID = "com.example.notifications.channel1"

            val repliedNotification = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText("Ваш заказ принят!")
                .build()

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationID, repliedNotification)
        }
    }
}