package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class reflexiones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Activar Edge to Edge
        setContentView(R.layout.activity_reflexiones)

        // Configuración del botón "El peso de un vaso de agua"
        val btnVaso = findViewById<Button>(R.id.button9) // Obtener el botón con ID button9
        btnVaso.setOnClickListener {
            // Intent para redirigir a la actividad Vaso
            val intent = Intent(this, vaso::class.java)
            startActivity(intent) // Iniciar la actividad Vaso
        }

        // Configuración del RadioButton para redirigir a la actividad Biblioteca
        val radioButton4 = findViewById<RadioButton>(R.id.radioButton4) // Obtener el RadioButton con ID radioButton4
        radioButton4.setOnClickListener {
            if (radioButton4.isChecked) { // Verificamos si el RadioButton está seleccionado
                // Intent para redirigir a la actividad Biblioteca
                val intent = Intent(this, BibliotecaActivity::class.java)
                startActivity(intent) // Iniciar la actividad Biblioteca
            }
        }

        // **Nuevo código**: Configuración del botón "La roca en el camino"
        val button10 = findViewById<Button>(R.id.button10) // Obtener el botón con ID button10
        button10.setOnClickListener {
            // Intent para redirigir a la actividad roca
            val intent = Intent(this, roca::class.java)
            startActivity(intent) // Iniciar la actividad roca
        }

        // Configuración del botón "El coleccionista de insultos"
        val button11 = findViewById<Button>(R.id.button11) // Obtener el botón con ID button11
        button11.setOnClickListener {
            // Intent para redirigir a la actividad Insultos
            val intent = Intent(this, insultos::class.java)
            startActivity(intent) // Iniciar la actividad Insultos
        }

        // Ajuste para que el contenido se muestre correctamente debajo de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
