package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class insultos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insultos)

        // Configuración del RadioButton para redirigir a la actividad InsultosDos
        val radioButton7 = findViewById<RadioButton>(R.id.radioButton7) // Obtener el RadioButton con ID radioButton7
        radioButton7.setOnClickListener {
            if (radioButton7.isChecked) { // Verificamos si el RadioButton está seleccionado
                // Intent para redirigir a la actividad InsultosDos
                val intent = Intent(this, insultosdos::class.java)
                startActivity(intent) // Iniciar la actividad InsultosDos
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
