package com.example.aplicacionlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class HistorialEmergencia : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var containerEmergencias: LinearLayout
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_emergencia)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        containerEmergencias = findViewById(R.id.containerEmergencias)

        cargarEmergencias()

        findViewById<Button>(R.id.btnCerrar).setOnClickListener {
            finish()
        }
    }

    private fun cargarEmergencias() {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        db.collection("usuarios")
            .document(userId)
            .collection("emergencias")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                containerEmergencias.removeAllViews()

                if (querySnapshot.isEmpty) {
                    val tvEmpty = TextView(this).apply {
                        text = "No hay emergencias registradas"
                        gravity = View.TEXT_ALIGNMENT_CENTER
                        setTextSize(16f)
                    }
                    containerEmergencias.addView(tvEmpty)
                    return@addOnSuccessListener
                }

                for (document in querySnapshot.documents) {
                    val mensaje = document.getString("mensaje") ?: ""
                    val timestamp = document.getDate("timestamp") ?: Date()

                    agregarEmergenciaALista(mensaje, timestamp)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al cargar emergencias: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun agregarEmergenciaALista(mensaje: String, fecha: Date) {
        val inflater = LayoutInflater.from(this)
        val itemView = inflater.inflate(R.layout.activity_item_emergencia, containerEmergencias, false)

        val tvFecha = itemView.findViewById<TextView>(R.id.tvFecha)
        val tvMensaje = itemView.findViewById<TextView>(R.id.tvMensaje)

        tvFecha.text = dateFormat.format(fecha)
        tvMensaje.text = mensaje

        containerEmergencias.addView(itemView)
    }
}