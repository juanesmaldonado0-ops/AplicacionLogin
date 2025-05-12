package com.example.aplicacionlogin

import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.MediaController
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

        // 1. Referencia al VideoView
        val videoView: VideoView = findViewById(R.id.videoViewEmpezar)

        // 2. URI del video en res/raw
        val videoUri: Uri = Uri.parse("android.resource://$packageName/raw/empezar")

        // 3. Asignar el video al VideoView
        try {
            videoView.setVideoURI(videoUri)
        } catch (e: Exception) {
            // Mostrar un mensaje de error si no se encuentra el video
            Toast.makeText(this, "Error al cargar el video", Toast.LENGTH_SHORT).show()
        }

        // 4. Hacer el VideoView en pantalla completa (opcional)
        videoView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        videoView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        // 5. Controles opcionales para controlar el video
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // 6. Reproducir automáticamente cuando el video esté listo
        videoView.setOnPreparedListener {
            videoView.start()
        }

        // 7. Márgenes de sistema para la interfaz
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_video_empezar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 8. Asegurarse de que la pantalla no se apague durante el video
        videoView.keepScreenOn = true

        // 9. Acción cuando el video termine (opcional)
        videoView.setOnCompletionListener {
            Toast.makeText(this, "¡Video completado!", Toast.LENGTH_SHORT).show()
            // finish()  // Si deseas regresar a la actividad anterior
        }
    }
}
