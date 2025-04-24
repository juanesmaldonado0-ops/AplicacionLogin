package com.example.aplicacionlogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LlamarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modo_emergencia1) // Reemplaza por el nombre correcto del XML

        // Botones de texto
        val btnPsicologia = findViewById<Button>(R.id.button15)
        val btnPsiquiatria = findViewById<Button>(R.id.button17)
        val btnNeurologia = findViewById<Button>(R.id.button18)

        // ImageButtons (llamadas simuladas)
        val btnCamara1 = findViewById<ImageButton>(R.id.btnCamara)
        val btnCamara2 = findViewById<ImageButton>(R.id.btnCamara2)
        val btnCamara3 = findViewById<ImageButton>(R.id.btnCamara3)

        btnPsicologia.setOnClickListener {
            Toast.makeText(this, "Has seleccionado Psicologia", Toast.LENGTH_SHORT).show()
        }

        btnPsiquiatria.setOnClickListener {
            Toast.makeText(this, "Has seleccionado Psiquiatria", Toast.LENGTH_SHORT).show()
        }

        btnNeurologia.setOnClickListener {
            Toast.makeText(this, "Has seleccionado Neurologia", Toast.LENGTH_SHORT).show()
        }

        // Simular llamada al presionar el boton de telefono
        btnCamara1.setOnClickListener {
            llamar("3001234567") // Reemplaza con numero real
        }

        btnCamara2.setOnClickListener {
            llamar("3007654321")
        }

        btnCamara3.setOnClickListener {
            llamar("3109876543")
        }
    }

    private fun llamar(numero: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$numero")
        startActivity(intent)
    }
}
