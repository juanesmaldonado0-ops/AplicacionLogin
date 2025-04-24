package com.example.aplicacionlogin;
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Diario_Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_emociones) // Asegúrate que coincide con tu XML

        // Configuración de los botones
        val btnEscribir = findViewById<Button>(R.id.btnEscribir)
        val btnDias = findViewById<Button>(R.id.btnDias)
        val btnFrases = findViewById<Button>(R.id.btnFrases)
        val btnVerRegistros = findViewById<Button>(R.id.btnVerRegistros)

        btnEscribir.setOnClickListener {
            // Redirigir a actividad para escribir en el diario
            val intent = Intent(this, EscribirDiarioActivity::class.java)
            startActivity(intent)
        }

        btnDias.setOnClickListener {
            // Mostrar días con registro
            val intent = Intent(this, DiasDiarioActivity::class.java)
            startActivity(intent)
        }

        btnFrases.setOnClickListener {
            // Mostrar frases de reflexión
            val intent = Intent(this, FrasesReflexionActivity::class.java)
            startActivity(intent)
        }

        btnVerRegistros.setOnClickListener {
            // Ver entradas anteriores
            val intent = Intent(this, VerRegistrosActivity::class.java)
            startActivity(intent)
        }
    }
}
