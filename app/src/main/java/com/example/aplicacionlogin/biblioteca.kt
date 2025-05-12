package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BibliotecaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biblioteca)

        findViewById<Button>(R.id.button5).setOnClickListener {
            startActivity(Intent(this, videos::class.java))
        }

        findViewById<Button>(R.id.button6).setOnClickListener {
            startActivity(Intent(this, reflexiones::class.java))
        }

        findViewById<Button>(R.id.button7).setOnClickListener {
            startActivity(Intent(this, frases::class.java))
        }

        findViewById<ImageButton>(R.id.imageButton2).setOnClickListener {
            startActivity(Intent(this, DiarioActivity::class.java))
        }

        findViewById<ImageButton>(R.id.imageButton3).setOnClickListener {
            startActivity(Intent(this, EmergenciaActivity::class.java))
        }

        findViewById<ImageButton>(R.id.imageButton4).setOnClickListener {
            startActivity(Intent(this, Afirmaciones::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
