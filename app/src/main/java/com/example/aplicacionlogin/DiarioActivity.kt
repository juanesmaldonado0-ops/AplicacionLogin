package com.example.aplicacionlogin;
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DiarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diario_de_emociones_1)

        val btnMenu = findViewById<Button>(R.id.btnMenu)
        btnMenu.setOnClickListener {
            val intent = Intent(this, Diario_Menu::class.java)
            startActivity(intent)
        }

    }
}
