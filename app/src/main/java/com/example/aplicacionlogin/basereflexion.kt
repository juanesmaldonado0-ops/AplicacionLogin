package com.example.aplicacionlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                    // Puedes mostrar un mensaje si falla la lectura
                }
            })
        }
    }

    inner class ReflexionAdapter(private val reflexiones: List<String>) :
        RecyclerView.Adapter<ReflexionAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textoReflexion: TextView = view.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val textView = LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1, parent, false
            )
            return ViewHolder(textView)
        }

        override fun getItemCount(): Int = reflexiones.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textoReflexion.text = reflexiones[position]
        }
    }
}
