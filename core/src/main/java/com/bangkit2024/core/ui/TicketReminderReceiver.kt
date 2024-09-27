package com.bangkit2024.core.ui

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.bangkit2024.core.R
import com.bangkit2024.core.domain.model.MovieTicket

class TicketReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val test = intent.getStringExtra(EXTRA_TEST)

        if (test != null) {
            showAlarmNotification(context, test)
            Log.d("TicketReminder", "Yeay berhasil")
        }
    }

    fun setTicketReminder(context: Context, movieTicket: MovieTicket) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intentToTicketReminderReceiver = Intent(context, TicketReminderReceiver::class.java)
        intentToTicketReminderReceiver.putExtra(EXTRA_TEST, "Test Reminder")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2024)
        calendar.set(Calendar.MONTH, 8)
        calendar.set(Calendar.DAY_OF_MONTH, 5)
        calendar.set(Calendar.HOUR_OF_DAY, 14)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            movieTicket.idTicket.take(3).toInt(),
            intentToTicketReminderReceiver,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(context, "Ticket reminder set up", Toast.LENGTH_SHORT).show()
    }

    fun cancelTicketReminder(context: Context, movieTicket: MovieTicket) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intentToTicketReminderReceiver = Intent(context, TicketReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            movieTicket.idTicket.take(3).toInt(),
            intentToTicketReminderReceiver,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Ticket reminder canceled", Toast.LENGTH_SHORT).show()
    }

    private fun showAlarmNotification(context: Context, test: String) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(test)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(notificationChannel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(NOTIF_ID, notification)
    }

    companion object {
        const val NOTIF_ID = 1
        const val EXTRA_TEST = "extra_test"
    }
}