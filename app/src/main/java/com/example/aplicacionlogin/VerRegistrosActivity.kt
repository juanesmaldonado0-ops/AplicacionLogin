package com.example.aplicacionlogin

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class VerRegistrosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registros) // Asegúrate que coincida con tu XML

        // Obtener referencias a los campos de fecha
        val editTextDate3 = findViewById<EditText>(R.id.editTextDate3)
        val editTextDate9 = findViewById<EditText>(R.id.editTextDate9)
        val editTextDate10 = findViewById<EditText>(R.id.editTextDate10)
        val editTextDate11 = findViewById<EditText>(R.id.editTextDate11)
        val editTextDate12 = findViewById<EditText>(R.id.editTextDate12)
        val editTextDate13 = findViewById<EditText>(R.id.editTextDate13)

        // Configurar fechas de ejemplo (deberías reemplazar esto con tus datos reales)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dates = listOf(
            Date(), // Fecha actual
            Date(System.currentTimeMillis() - 86400000), // Ayer
            Date(System.currentTimeMillis() - 172800000), // Hace 2 días
            Date(System.currentTimeMillis() - 259200000), // Hace 3 días
            Date(System.currentTimeMillis() - 345600000), // Hace 4 días
            Date(System.currentTimeMillis() - 432000000)  // Hace 5 días
        )

        // Asignar fechas a los EditText
        listOf(editTextDate3, editTextDate9, editTextDate10, editTextDate11, editTextDate12, editTextDate13)
            .forEachIndexed { index, editText ->
                if (index < dates.size) {
                    editText.setText(dateFormat.format(dates[index]))
                    editText.isFocusable = false // Hacer que no sean editables
                    editText.isClickable = true // Permitir clicks para ver detalles
                    }
                }
            }
    }
