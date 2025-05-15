package com.example.aplicacionlogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.RadioButton
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class empezar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_empezar)

        val videoView: VideoView = findViewById(R.id.videoViewEmpezar)
        val videoUri: Uri = Uri.parse("android.resource://$packageName/raw/empezar")

        try {
            videoView.setVideoURI(videoUri)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar el video", Toast.LENGTH_SHORT).show()
        }

        videoView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        videoView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnPreparedListener {
            videoView.start()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_video_empezar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        videoView.keepScreenOn = true

        videoView.setOnCompletionListener {
            Toast.makeText(this, "¡Video completado!", Toast.LENGTH_SHORT).show()
        }

        // 💡 Acción del botón RadioButton
        val radioButton: RadioButton = findViewById(R.id.radioButton10)
        radioButton.setOnClickListener {
            val intent = Intent(this, videos::class.java)
            startActivity(intent)
        }
    }
}
