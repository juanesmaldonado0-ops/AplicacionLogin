package com.example.aplicacionlogin

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class escuchalo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escuchalo)

        // 1. Referencia al VideoView
        val videoView: VideoView = findViewById(R.id.videoView)

        // 2. URI del video en res/raw
        val videoUri: Uri = Uri.parse("android.resource://$packageName/raw/escuchalo")

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

        // 6. Márgenes de sistema para la interfaz
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_video)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
