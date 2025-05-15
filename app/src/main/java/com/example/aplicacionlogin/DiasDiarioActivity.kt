package com.example.aplicacionlogin

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DiasDiarioActivity : AppCompatActivity() {

    private lateinit var txtDia: TextView
    private lateinit var txtMensajeDia: TextView
    private lateinit var sharedPref: SharedPreferences
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_dias)

        // Inicializar vistas
        txtDia = findViewById(R.id.txtDia)
        txtMensajeDia = findViewById(R.id.txtMensajeDia)
        sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Obtener el streak
        getCurrentStreak()
    }

    private fun getCurrentStreak() {
        val userId = auth.currentUser?.uid ?: return

        // Opción 1: Obtener de Firestore (en tiempo real)
        db.collection("usuarios").document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    showError()
                    return@addSnapshotListener
                }

                val streak = snapshot?.getLong("loginStreak")?.toInt() ?: 1
                updateUI(streak)
            }

        // Opción 2: Obtener de caché local (más rápido)
        val cachedStreak = sharedPref.getInt("currentStreak", 1)
        updateUI(cachedStreak)
    }

    private fun updateUI(streakDays: Int) {
        txtDia.text = streakDays.toString()

        val message = when (streakDays) {
            1 -> "$streakDays día escribiendo tu historia\nCada palabra ha sido\nun paso en tu camino de crecimiento."
            else -> "$streakDays días escribiendo tu historia\nCada palabra ha sido\nun paso en tu camino de crecimiento."
        }

        txtMensajeDia.text = message
    }

    private fun showError() {
        txtDia.text = "1" // Valor por defecto
        txtMensajeDia.text = "Día escribiendo tu historia\nCada paso cuenta en tu crecimiento."
    }
}