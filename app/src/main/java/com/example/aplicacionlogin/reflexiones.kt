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
        enableEdgeToEdge()
        setContentView(R.layout.activity_reflexiones)

        // Botón "El peso de un vaso de agua"
        val btnVaso = findViewById<Button>(R.id.button9)
        btnVaso.setOnClickListener {
            val intent = Intent(this, vaso::class.java)
            startActivity(intent)
        }

        // Botón "La roca en el camino"
        val button10 = findViewById<Button>(R.id.button10)
        button10.setOnClickListener {
            val intent = Intent(this, roca::class.java)
            startActivity(intent)
        }

        // Botón "El coleccionista de insultos"
        val button11 = findViewById<Button>(R.id.button11)
        button11.setOnClickListener {
            val intent = Intent(this, insultos::class.java)
            startActivity(intent)
        }

        // RadioButton para ir a la Biblioteca
        val radioButton4 = findViewById<RadioButton>(R.id.radioButton4)
        radioButton4.setOnClickListener {
            if (radioButton4.isChecked) {
                val intent = Intent(this, BibliotecaActivity::class.java)
                startActivity(intent)
            }
        }

        // ✅ Botón "Mis reflexiones" para abrir activity_basereflexion.xml
        val button21 = findViewById<Button>(R.id.button21)
        button21.setOnClickListener {
            val intent = Intent(this, basereflexion::class.java)
            startActivity(intent)
        }

        // Ajuste visual para Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
