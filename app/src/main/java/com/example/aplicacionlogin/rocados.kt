package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class rocados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Activar Edge to Edge
        setContentView(R.layout.activity_rocados)

        // Configuración del RadioButton para redirigir a la actividad Reflexiones
        val radioButton5 = findViewById<RadioButton>(R.id.radioButton5) // Obtener el RadioButton con ID radioButton5
        radioButton5.setOnClickListener {
            if (radioButton5.isChecked) { // Verificamos si el RadioButton está seleccionado
                // Intent para redirigir a la actividad Reflexiones
                val intent = Intent(this, reflexiones::class.java)
                startActivity(intent) // Iniciar la actividad Reflexiones
            }
        }

        // Dirección del botón button22 hacia rocareflexion
        val button22 = findViewById<Button>(R.id.button22)
        button22.setOnClickListener {
            val intent = Intent(this, rocareflexion::class.java)
            startActivity(intent)
        }

        // Ajuste para que el contenido se muestre correctamente debajo de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
