package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityRegistro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "ActivityRegistro" // Para logs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()

        val nombre = findViewById<EditText>(R.id.editTextText)
        val correo = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val contrasena = findViewById<EditText>(R.id.editTextTextPassword2)
        val fecha = findViewById<EditText>(R.id.editTextDate)
        val identificacion = findViewById<EditText>(R.id.editTextTextMultiLine)
        val apoyoDeseado = findViewById<EditText>(R.id.editTextTextMultiLine2)
        val botonListo = findViewById<Button>(R.id.button3)

        botonListo.setOnClickListener {
            val nombreTexto = nombre.text.toString().trim()
            val correoTexto = correo.text.toString().trim()
            val contrasenaTexto = contrasena.text.toString().trim()
            val fechaTexto = fecha.text.toString().trim()
            val identificacionTexto = identificacion.text.toString().trim()
            val apoyoTexto = apoyoDeseado.text.toString().trim()

            if (correoTexto.isEmpty() || contrasenaTexto.isEmpty() || nombreTexto.isEmpty()) {
                mostrarToast("Completa todos los campos requeridos")
                return@setOnClickListener
            }

            registrarUsuario(nombreTexto, correoTexto, contrasenaTexto, fechaTexto, identificacionTexto, apoyoTexto)
        }
    }

    private fun registrarUsuario(nombre: String, correo: String, contrasena: String,
                                 fecha: String, identificacion: String, apoyo: String) {
        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    guardarDatosUsuario(auth.currentUser?.uid, nombre, correo, fecha, identificacion, apoyo)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    mostrarToast("Error en registro: ${task.exception?.message}")
                }
            }
    }

    private fun guardarDatosUsuario(userId: String?, nombre: String, correo: String,
                                    fecha: String, identificacion: String, apoyo: String) {
        if (userId == null) {
            mostrarToast("Error: ID de usuario no generado")
            return
        }

        val userMap = hashMapOf(
            "nombre" to nombre,
            "correo" to correo,
            "fechaNacimiento" to fecha,
            "identificacion" to identificacion,
            "apoyoDeseado" to apoyo
        )

        FirebaseDatabase.getInstance().getReference("usuarios")
            .child(userId)
            .setValue(userMap)
            .addOnSuccessListener {
                Log.d(TAG, "Datos de usuario guardados correctamente")
                mostrarToast("Registro exitoso")
                redirigirABienvenida()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al guardar datos", e)
                mostrarToast("Error al guardar datos: ${e.message}")
            }
    }

    private fun redirigirABienvenida() {
        try {
            val intent = Intent(this, bienvenida::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
            Log.d(TAG, "Redirección a bienvenida iniciada")
        } catch (e: Exception) {
            Log.e(TAG, "Error al redirigir a bienvenida", e)
            mostrarToast("Error al abrir la pantalla de bienvenida")
        }
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}