package com.example.aplicacionlogin

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class EscribirDiarioActivity : AppCompatActivity() {

    private lateinit var editDate: EditText
    private lateinit var editText: EditText
    private val calendar = Calendar.getInstance()
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.escribir_diario)

        // Inicializar Firebase Database
        database = FirebaseDatabase.getInstance()

        // Inicializar vistas
        editDate = findViewById(R.id.editDate)
        editText = findViewById(R.id.editText)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Configurar fecha actual
        setCurrentDate()

        // DatePicker
        editDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Botón Guardar
        btnGuardar.setOnClickListener {
            guardarEntradaDiario()
        }
    }

    private fun setCurrentDate() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        editDate.setText(dateFormat.format(calendar.time))
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                setCurrentDate()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun guardarEntradaDiario() {
        val texto = editText.text.toString().trim()
        val fecha = editDate.text.toString().trim()

        if (texto.isEmpty()) {
            Toast.makeText(this, "El texto no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear estructura de datos
        val entrada = hashMapOf(
            "fecha" to fecha,
            "contenido" to texto,
            "timestamp" to System.currentTimeMillis()
        )

        // Guardar en Firebase
        database.reference.child("diario_entradas")
            .push() // Genera ID único
            .setValue(entrada)
            .addOnSuccessListener {
                Toast.makeText(this, "Entrada guardada", Toast.LENGTH_SHORT).show()
                editText.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
    }
}