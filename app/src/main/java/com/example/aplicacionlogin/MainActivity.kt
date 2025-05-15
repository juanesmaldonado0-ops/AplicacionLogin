package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Auth
        auth = Firebase.auth

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

            // Mostrar progreso (opcional)
            // progressBar.visibility = View.VISIBLE

            // Autenticación con Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    // progressBar.visibility = View.GONE

                    if (task.isSuccessful) {
                        // Redirigir a la actividad biblioteca
                        startActivity(Intent(this, BibliotecaActivity::class.java))
                        finish() // Cierra la actividad de login
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
}