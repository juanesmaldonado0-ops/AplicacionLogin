package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityRegistro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "ActivityRegistro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

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

            auth.createUserWithEmailAndPassword(correoTexto, contrasenaTexto)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                        val userMap = hashMapOf(
                            "nombre" to nombreTexto,
                            "correo" to correoTexto,
                            "fechaNacimiento" to fechaTexto,
                            "identificacion" to identificacionTexto,
                            "apoyoDeseado" to apoyoTexto
                        )

                        FirebaseDatabase.getInstance().getReference("usuarios")
                            .child(userId)
                            .setValue(userMap)
                            .addOnSuccessListener {
                                // Redirección explícita a MainActivity
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this,
                                    "Error al guardar datos: ${e.message}",
                                    Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this,
                            "Error en registro: ${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
