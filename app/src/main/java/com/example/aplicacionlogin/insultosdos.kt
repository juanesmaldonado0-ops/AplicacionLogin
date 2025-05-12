package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class insultosdos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insultosdos)

        // Configuración del RadioButton para redirigir a la actividad InsultosTres
        val radioButton8 = findViewById<RadioButton>(R.id.radioButton8) // Obtener el RadioButton con ID radioButton8
        radioButton8.setOnClickListener {
            if (radioButton8.isChecked) { // Verificamos si el RadioButton está seleccionado
                // Intent para redirigir a la actividad InsultosTres
                val intent = Intent(this, insultotres::class.java)
                startActivity(intent) // Iniciar la actividad InsultosTres
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
