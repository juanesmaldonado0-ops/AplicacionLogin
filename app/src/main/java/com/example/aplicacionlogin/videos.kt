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

        val cardLiberate = findViewById<MaterialCardView>(R.id.cardVideo1)
        val cardEscuchalo = findViewById<MaterialCardView>(R.id.cardVideo2)
        val cardEmpezar = findViewById<MaterialCardView>(R.id.cardVideo4)
        val cardContigo = findViewById<MaterialCardView>(R.id.cardVideo3)

        cardLiberate.setOnClickListener {
            val intent = Intent(this, Liberate::class.java)
            startActivity(intent)
        }

        cardEscuchalo.setOnClickListener {
            val intent = Intent(this, escuchalo::class.java)
            startActivity(intent)
        }

        cardEmpezar.setOnClickListener {
            val intent = Intent(this, empezar::class.java)
            startActivity(intent)
        }

        cardContigo.setOnClickListener {
            val intent = Intent(this, contigo::class.java)
            startActivity(intent)
        }

        // Nuevo: redirige a biblioteca.kt cuando se da clic en el RadioButton
        val radioButton = findViewById<android.widget.RadioButton>(R.id.radioButton)
        radioButton.setOnClickListener {
            val intent = Intent(this, BibliotecaActivity::class.java)
            startActivity(intent)
        }
    }
}
