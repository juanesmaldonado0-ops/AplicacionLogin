package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
<<<<<<< HEAD
=======
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
>>>>>>> 6bb22eb472787f9575ce9fba83185f5dcb4fea6c

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

<<<<<<< HEAD
        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val btnIniciarSesion = findViewById<Button>(R.id.button2)
        val btnRegistro = findViewById<Button>(R.id.button)

        btnIniciarSesion.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, biblioteca::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                Log.e("Auth", "Error: ${task.exception?.message}")
                            }
                        }
                } else {
                    Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, ActivityRegistro::class.java)
            startActivity(intent)
=======
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
                        startActivity(Intent(this, biblioteca::class.java))
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
>>>>>>> 6bb22eb472787f9575ce9fba83185f5dcb4fea6c
        }
    }
}