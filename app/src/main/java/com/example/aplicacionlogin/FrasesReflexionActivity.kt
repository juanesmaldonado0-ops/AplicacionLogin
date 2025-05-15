package com.example.aplicacionlogin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FrasesReflexionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_frases) // Asegúrate que el nombre del layout coincida

        // Configuración de los botones
        val btnMisFrases = findViewById<Button>(R.id.button12)
        val btnGuardar = findViewById<Button>(R.id.button13)
        val tvMensaje = findViewById<TextView>(R.id.textView29)

        btnMisFrases.setOnClickListener {
            // Aquí iría la lógica para ver frases guardadas
        }

        btnGuardar.setOnClickListener {
            // Aquí iría la lógica para guardar frases
        }
    }
}