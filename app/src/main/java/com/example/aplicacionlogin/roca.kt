package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class roca : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_roca)

        // Configuración de la vista para Edge-to-Edge (como ya tienes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Encuentra el RadioButton por su ID
        val radioButton6 = findViewById<RadioButton>(R.id.radioButton6)

        // Configura un OnClickListener
        radioButton6.setOnClickListener {
            // Cuando el RadioButton sea clickeado, se abrirá activity_rocados
            val intent = Intent(this, rocados::class.java)
            startActivity(intent)
        }
    }
}
