package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class videos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_videos)

        // Encuentra las tarjetas por sus IDs
        val cardLiberate = findViewById<MaterialCardView>(R.id.cardVideo1)
        val cardEscuchalo = findViewById<MaterialCardView>(R.id.cardVideo2)
        val cardEmpezar = findViewById<MaterialCardView>(R.id.cardVideo4)
        val cardContigo = findViewById<MaterialCardView>(R.id.cardVideo3) // Aquí agregamos el nuevo botón

        // Redirige a la actividad Liberate cuando se hace clic en la tarjeta correspondiente
        cardLiberate.setOnClickListener {
            val intent = Intent(this, Liberate::class.java)
            startActivity(intent)
        }

        // Redirige a la actividad Escuchalo cuando se hace clic en la tarjeta correspondiente
        cardEscuchalo.setOnClickListener {
            val intent = Intent(this, escuchalo::class.java) // Redirección a la actividad escuchalo
            startActivity(intent)
        }

        // Redirige a la actividad Empezar cuando se hace clic en la tarjeta correspondiente
        cardEmpezar.setOnClickListener {
            val intent = Intent(this, empezar::class.java) // Redirección a la actividad empezar
            startActivity(intent)
        }

        // Redirige a la actividad Contigo cuando se hace clic en la tarjeta correspondiente
        cardContigo.setOnClickListener {
            val intent = Intent(this, contigo::class.java) // Redirige a la actividad Contigo
            startActivity(intent)
        }
    }
}
