package com.example.notifier

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var tokenText: TextView
    private lateinit var copyButton: Button

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(this, "تم رفض إذن الإشعارات (يمكن تغييره لاحقًا من الإعدادات)", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationUtils.createNotificationChannel(this)

        tokenText = findViewById(R.id.token_value)
        copyButton = findViewById(R.id.copy_button)

        if (Build.VERSION.SDK_INT >= 33) {
            val granted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) requestPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        FirebaseMessaging.getInstance().subscribeToTopic("announcements")

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            tokenText.text = token
        }.addOnFailureListener {
            tokenText.text = "تعذر الحصول على التوكن"
        }

        copyButton.setOnClickListener {
            val token = tokenText.text?.toString() ?: ""
            if (token.isNotEmpty()) {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.setPrimaryClip(ClipData.newPlainText("FCM Token", token))
                Toast.makeText(this, "تم نسخ الـ token", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
