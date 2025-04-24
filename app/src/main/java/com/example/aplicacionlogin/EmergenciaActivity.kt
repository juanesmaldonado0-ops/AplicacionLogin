package com.example.aplicacionlogin

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity

class EmergenciaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modo_emergencia) // reemplaza con el nombre real de tu XML

        val textInput = findViewById<TextInputEditText>(R.id.textInputEditText)
        val confirmarBtn = findViewById<Button>(R.id.button15)
        confirmarBtn.setOnClickListener {
            val intent = Intent(this, LlamarActivity::class.java)
            startActivity(intent)
        }
    }
}
