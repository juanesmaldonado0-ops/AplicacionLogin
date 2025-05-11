package com.example.aplicacionlogin

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import androidx.appcompat.app.AppCompatActivity

class EmergenciaActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modo_emergencia)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val textInput = findViewById<TextInputEditText>(R.id.textInputEditText)
        val confirmarBtn = findViewById<Button>(R.id.button15)

        confirmarBtn.setOnClickListener {
            val mensaje = textInput.text.toString().trim()


            if (mensaje.isEmpty()) {
                Toast.makeText(this, "Por favor, describe el problema", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val userId = auth.currentUser?.uid
            if (userId == null) {
                Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val notaEmergencia = hashMapOf(
                "mensaje" to mensaje,
                "timestamp" to Calendar.getInstance().time
            )


            confirmarBtn.isEnabled = false


            db.collection("usuarios")
                .document(userId)
                .collection("emergencias")
                .add(notaEmergencia)
                .addOnSuccessListener {

                    Toast.makeText(this, "Mensaje de emergencia enviado", Toast.LENGTH_SHORT).show()
                    textInput.text?.clear()
                    val intent = Intent(this, LlamarActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->

                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    confirmarBtn.isEnabled = true // Rehabilitar el botón si ocurre un error
                }
        }
    }
}
