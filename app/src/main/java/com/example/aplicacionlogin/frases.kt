package com.example.aplicacionlogin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class frases : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_frases)

        // Aplicar los márgenes para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el nuevo RadioButton13 para redirigir a la actividad biblioteca
        val radioButton13 = findViewById<RadioButton>(R.id.radioButton13)
        radioButton13.setOnClickListener {
            if (radioButton13.isChecked) {
                val intent = Intent(this, BibliotecaActivity::class.java) // Cambia a Biblioteca::class.java si tu clase usa mayúscula
                startActivity(intent)
            }
        }
    }
}
