package com.example.aplicacionlogin

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class EscribirDiarioActivity : AppCompatActivity() {

    private lateinit var editDate: EditText
    private lateinit var editText: EditText
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.escribir_diario)

        // Inicializar vistas
        editDate = findViewById(R.id.editDate)
        editText = findViewById(R.id.editText)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCamara = findViewById<ImageButton>(R.id.btnCamara)
        val btnMicrofono = findViewById<ImageButton>(R.id.btnMicrofono)
        val btnEmoji = findViewById<ImageButton>(R.id.btnEmoji)

        // Configurar fecha actual por defecto
        setCurrentDate()

        // Configurar DatePicker cuando se hace clic en editDate
        editDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Botón de volver
        backButton.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la anterior
        }

        // Botón de guardar
        btnGuardar.setOnClickListener {
            guardarEntradaDiario()
        }

        // Botones de herramientas (implementación básica)
        btnCamara.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de cámara", Toast.LENGTH_SHORT).show()
        }

        btnMicrofono.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de micrófono", Toast.LENGTH_SHORT).show()
        }

        btnEmoji.setOnClickListener {
            Toast.makeText(this, "Selector de emojis", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCurrentDate() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        editDate.setText(dateFormat.format(calendar.time))
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                setCurrentDate()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun guardarEntradaDiario() {
        val texto = editText.text.toString()
        val fecha = editDate.text.toString()

        if (texto.isEmpty()) {
            Toast.makeText(this, "Por favor escribe algo en tu diario", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí iría la lógica para guardar en base de datos
        Toast.makeText(this, "Entrada guardada para $fecha", Toast.LENGTH_SHORT).show()

        // Limpiar campo después de guardar
        editText.text.clear()
    }
}