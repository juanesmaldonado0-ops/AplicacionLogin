package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class insultotres : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insultotres)

        // Configuración del RadioButton para redirigir a la actividad Reflexiones
        val radioButton9 = findViewById<RadioButton>(R.id.radioButton9) // Obtener el RadioButton con ID radioButton9
        radioButton9.setOnClickListener {
            if (radioButton9.isChecked) { // Verificamos si el RadioButton está seleccionado
                // Intent para redirigir a la actividad Reflexiones
                val intent = Intent(this, reflexiones::class.java)
                startActivity(intent) // Iniciar la actividad Reflexiones
            }
        }

        // Ajuste para que el contenido se muestre correctamente debajo de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
