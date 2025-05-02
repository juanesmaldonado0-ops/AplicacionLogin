package com.example.aplicacionlogin

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class EscribirDiarioActivity : AppCompatActivity() {

    private lateinit var editDate: EditText
    private lateinit var editText: EditText
    private val calendar = Calendar.getInstance()
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.escribir_diario)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inicializar vistas
        editDate = findViewById(R.id.editDate)
        editText = findViewById(R.id.editText)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Configurar fecha actual
        setCurrentDate()

        // Configurar DatePicker
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
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "Debes iniciar sesión para guardar entradas", Toast.LENGTH_SHORT).show()
            return
        }

        val texto = editText.text.toString().trim()
        val fecha = editDate.text.toString().trim()

        if (texto.isEmpty()) {
            Toast.makeText(this, "El texto no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear objeto de entrada
        val entrada = hashMapOf(
            "fecha" to fecha,
            "contenido" to texto,
            "timestamp" to Calendar.getInstance().time
        )

        // Guardar en la subcolección diario del usuario
        db.collection("usuarios")
            .document(user.uid)
            .collection("diario")
            .add(entrada)
            .addOnSuccessListener {
                Toast.makeText(this, "✅ Entrada guardada con éxito", Toast.LENGTH_SHORT).show()
                editText.text.clear()

                // Actualizar contador de entradas
                actualizarContadorEntradas(user.uid)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "❌ Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarContadorEntradas(userId: String) {
        val userRef = db.collection("usuarios").document(userId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentCount = snapshot.getLong("totalEntradas") ?: 0
            transaction.update(userRef, "totalEntradas", currentCount + 1)
        }.addOnSuccessListener {
            Log.d("Diario", "Contador actualizado")
        }.addOnFailureListener { e ->
            Log.w("Diario", "Error actualizando contador", e)
        }
    }
}