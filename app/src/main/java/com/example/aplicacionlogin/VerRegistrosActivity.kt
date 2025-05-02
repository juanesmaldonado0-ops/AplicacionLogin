package com.example.aplicacionlogin

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class VerRegistrosActivity : AppCompatActivity() {

    // Variables de Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // Formateador de fecha
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Arrays para manejar las vistas
    private lateinit var tvFechas: Array<TextView>
    private lateinit var tvContenidos: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registros)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inicializar arrays de vistas
        tvFechas = arrayOf(
            findViewById(R.id.tvFecha1),
            findViewById(R.id.tvFecha2),
            findViewById(R.id.tvFecha3),
            findViewById(R.id.tvFecha4),
            findViewById(R.id.tvFecha5),
            findViewById(R.id.tvFecha6)
        )

        tvContenidos = arrayOf(
            findViewById(R.id.tvContenido1),
            findViewById(R.id.tvContenido2),
            findViewById(R.id.tvContenido3),
            findViewById(R.id.tvContenido4),
            findViewById(R.id.tvContenido5),
            findViewById(R.id.tvContenido6)
        )

        // Cargar datos desde Firestore
        cargarRegistros()
    }

    private fun cargarRegistros() {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        db.collection("usuarios")
            .document(userId)
            .collection("diario")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(6)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Toast.makeText(this, "No hay registros disponibles", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // Ocultar todas las vistas primero
                tvFechas.forEach { it.visibility = TextView.GONE }
                tvContenidos.forEach { it.visibility = TextView.GONE }

                // Mostrar solo los registros existentes
                querySnapshot.documents.forEachIndexed { index, document ->
                    if (index < 6) { // Asegurarnos que no excedemos el número de vistas
                        val fecha = document.getDate("timestamp") ?: Date()
                        val contenido = document.getString("contenido") ?: ""

                        tvFechas[index].text = dateFormat.format(fecha)
                        tvContenidos[index].text = contenido

                        tvFechas[index].visibility = TextView.VISIBLE
                        tvContenidos[index].visibility = TextView.VISIBLE
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar registros: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}