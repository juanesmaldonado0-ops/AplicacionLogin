package com.example.aplicacionlogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.RadioButton
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Liberate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_liberate)

        // 1. Referencia al VideoView
        val videoView: VideoView = findViewById(R.id.videoView)

        // 2. URI del video en res/raw
        val videoUri: Uri = Uri.parse("android.resource://$packageName/raw/liberate")

        // 3. Asignar el video al VideoView
        videoView.setVideoURI(videoUri)

        // 4. Controles opcionales para controlar el video
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // 5. Reproducir automáticamente cuando el video esté listo
        videoView.setOnPreparedListener {
            videoView.start()
        }

        // 6. Márgenes de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_video)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 7. Referencia al RadioButton y asignar OnClickListener
        val radioButton: RadioButton = findViewById(R.id.radioButton14)
        radioButton.setOnClickListener {
            val intent = Intent(this, videos::class.java)
            startActivity(intent)
        }
    }
}
