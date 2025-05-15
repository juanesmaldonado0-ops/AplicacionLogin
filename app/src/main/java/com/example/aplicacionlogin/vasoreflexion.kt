package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class vasoreflexion : AppCompatActivity() {

    private lateinit var reflexionEditText: EditText
    private lateinit var guardarButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vasoreflexion)

        reflexionEditText = findViewById(R.id.editReflexion)
        guardarButton = findViewById(R.id.btnGuardar)
        database = FirebaseDatabase.getInstance().reference

        guardarButton.setOnClickListener {
            val reflexion = reflexionEditText.text.toString().trim()
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (reflexion.isNotEmpty() && userId != null) {
                val reflexionId = database.child("reflexiones").child(userId).push().key
                val data = hashMapOf(
                    "id" to reflexionId,
                    "reflexion" to reflexion
                )

                if (reflexionId != null) {
                    database.child("reflexiones").child(userId).child(reflexionId)
                        .setValue(data)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Reflexión guardada correctamente.", Toast.LENGTH_SHORT).show()

                            // Limpiar el campo de texto
                            reflexionEditText.text.clear()

                            // Redirigir a la pantalla de base de reflexiones
                            val intent = Intent(this, basereflexion::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al guardar.", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "Escribe una reflexión.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
