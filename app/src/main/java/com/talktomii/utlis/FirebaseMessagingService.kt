package com.talktomii.utlis

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.talktomii.R
import com.talktomii.ui.home.HomeActivity
import com.talktomii.ui.loginSignUp.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject

class FireBaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.let {
            println("---Notifications " + it.notification.toString())
        }

        val jsonObject = JSONObject(message.data as Map<*, *>)
        try {
            println("---Notifications firebseData $jsonObject")
            addNotification(jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
//{"notification_type":"ORDER_STATUS_UPDATE","notification_title":"Order Status Updated","action":"prepared","driver":"","type":"Food","service_type":"Test Company Owner","notification_message":"Your Order is accepted by Test Company Owner","order_id":"256"}

    private fun addNotification(jsonObject: JSONObject) {
        var message = ""
        if (jsonObject.has("notification_message")) {
            message = jsonObject.getString("notification_message")
        }
        val channelId = "my_channel_01"
        val name = "my_channel"
        if(message=="Status : Service Completed"){
            var intent=Intent("COMPLETED")
            intent.putExtra("order_id",jsonObject.getString("order_id"))
            sendBroadcast(intent)
        }


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(mChannel)
        }

        val stackBuilder = TaskStackBuilder.create(this)
        val resultIntent: Intent
        if (jsonObject.has("type") && jsonObject.getString("type") == "make_order_payment" || jsonObject.getString(
                "type"
            ) == "order_prepared"
        ) {
            resultIntent = Intent(applicationContext, MainActivity::class.java)
            resultIntent.putExtra("id", jsonObject.getInt("order_id"))
        } else resultIntent = Intent(applicationContext, HomeActivity::class.java)
        stackBuilder.addParentStack(HomeActivity::class.java)
        resultIntent.putExtra("NOTIFICATION_ID", 0)
        resultIntent.putExtra("json", jsonObject.toString())
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        stackBuilder.addNextIntent(resultIntent)

        val resultPendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(jsonObject.getString("title"))
            .setContentText(message)
            .setContentIntent(resultPendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher) // Required!
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
            .setColor(Color.TRANSPARENT)
        notificationManager.notify(0, builder.build())
    }

}
