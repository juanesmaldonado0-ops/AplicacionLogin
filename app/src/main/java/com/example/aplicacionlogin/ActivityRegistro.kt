package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ActivityRegistro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializar Firebase Auth y Firestore
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

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
                Toast.makeText(this, "Completa todos los campos requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registrarUsuarioFirestore(nombreTexto, correoTexto, contrasenaTexto, fechaTexto, identificacionTexto, apoyoTexto)
        }
    }

    private fun registrarUsuarioFirestore(
        nombre: String,
        correo: String,
        contrasena: String,
        fechaNacimiento: String,
        identificacion: String,
        apoyoDeseado: String
    ) {
        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    // Actualizar perfil con nombre
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(nombre)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Guardar datos en Firestore
                                guardarDatosFirestore(
                                    userId = user.uid,
                                    nombre = nombre,
                                    correo = correo,
                                    fechaNacimiento = fechaNacimiento,
                                    identificacion = identificacion,
                                    apoyoDeseado = apoyoDeseado
                                )
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al actualizar perfil: ${profileTask.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Error en registro: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun guardarDatosFirestore(
        userId: String,
        nombre: String,
        correo: String,
        fechaNacimiento: String,
        identificacion: String,
        apoyoDeseado: String
    ) {
        val userData = hashMapOf(
            "nombre" to nombre,
            "correo" to correo,
            "fechaNacimiento" to fechaNacimiento,
            "identificacion" to identificacion,
            "apoyoDeseado" to apoyoDeseado,
            "fechaRegistro" to Calendar.getInstance().time,
            "diario" to hashMapOf(
                "entradas_count" to 0
            )
        )

        db.collection("usuarios")
            .document(userId)
            .set(userData)
            .addOnSuccessListener {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error al guardar datos: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}

