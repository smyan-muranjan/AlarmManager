package com.example.alarmmanager

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("Received Broadcast")
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        if (context == null) {
            return
        }

        val onClickIntent = Intent(context, MainActivity::class.java)
        val onClickPendingIntent = PendingIntent.getActivity(
            context,
            0,
            onClickIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification =
            NotificationCompat.Builder(context, "scheduled_notification").run {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentText(message)
                setContentTitle("Scheduled Reminder")
                setContentIntent(onClickPendingIntent)
                build()
            }


        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details
                return
            }
            notify(1, notification)
        }
    }
}