package com.example.aplicacionlogin

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class basereflexion : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReflexionAdapter
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val reflexiones = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basereflexion)

        recyclerView = findViewById(R.id.recyclerReflexiones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ReflexionAdapter(reflexiones)
        recyclerView.adapter = adapter

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val userId = auth.currentUser?.uid
        if (userId != null) {
            val reflexionesRef = database.child("reflexiones").child(userId)
            reflexionesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    reflexiones.clear()
                    for (child in snapshot.children) {
                        val texto = child.child("reflexion").getValue(String::class.java)
                        texto?.let { reflexiones.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejo de error
                }
            })
        }
    }

    inner class ReflexionAdapter(private val reflexiones: List<String>) :
        RecyclerView.Adapter<ReflexionAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textoReflexion: TextView = view.findViewById(1000)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // Crear un LinearLayout que actúa como contenedor con padding y margen (panel)
            val container = LinearLayout(parent.context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(30, 30, 30, 30)
                setBackgroundColor(Color.parseColor("#F0F0F0")) // color gris claro de fondo
                val params = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(20, 20, 20, 20) // margen entre paneles
                layoutParams = params
                elevation = 8f // sombra para que parezca tarjeta (si está soportado)
            }

            // Crear el TextView con id fijo 1000 para luego referenciarlo
            val texto = TextView(parent.context).apply {
                id = 1000
                textSize = 18f
                setTextColor(Color.parseColor("#152066")) // azul oscuro como en tu app
                setLineSpacing(1.2f, 1.2f)
                gravity = Gravity.START
            }

            container.addView(texto)

            return ViewHolder(container)
        }

        override fun getItemCount(): Int = reflexiones.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textoReflexion.text = reflexiones[position]
        }
    }
}
