package com.example.aplicacionlogin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Configurar vistas
        val etEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val etPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.button2)
        val btnRegister = findViewById<Button>(R.id.button)

        // Botón de Registro
        btnRegister.setOnClickListener {
            startActivity(Intent(this, ActivityRegistro::class.java))
        }

        // Botón de Login
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingrese email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Autenticación con Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Actualizar el streak en Firestore
                        updateLoginStreak(auth.currentUser?.uid ?: "")
                        // Redirigir a BibliotecaActivity
                        startActivity(Intent(this, BibliotecaActivity::class.java))
                        finish()
                    } else {
                        val errorMsg = when {
                            task.exception?.message?.contains("invalid email") == true -> "Formato de email inválido"
                            task.exception?.message?.contains("wrong password") == true -> "Contraseña incorrecta"
                            task.exception?.message?.contains("user not found") == true -> "Usuario no registrado"
                            else -> "Error de autenticación: ${task.exception?.message}"
                        }
                        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun updateLoginStreak(userId: String) {
        val userRef = db.collection("usuarios").document(userId)
        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val lastLoginDate = snapshot.getDate("lastLoginDate")
            val yesterday = Calendar.getInstance().apply {
                add(Calendar.DATE, -1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val newStreak = when {
                lastLoginDate == null -> 1 // Primer login
                lastLoginDate == currentDate -> snapshot.getLong("loginStreak")?.toInt() ?: 1 // Ya hizo login hoy
                lastLoginDate == yesterday -> (snapshot.getLong("loginStreak")?.toInt() ?: 0) + 1 // Login consecutivo
                else -> 1 // Streak roto (reiniciar a 1)
            }

            // Actualizar Firestore
            transaction.update(userRef,
                "lastLoginDate", currentDate,
                "loginStreak", newStreak
            )
            newStreak
        }.addOnSuccessListener { streak ->
            // Guardar en SharedPreferences para acceso rápido
            sharedPref.edit().putInt("currentStreak", streak).apply()
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error al actualizar el streak: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}