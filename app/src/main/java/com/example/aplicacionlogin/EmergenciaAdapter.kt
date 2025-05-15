package com.example.aplicacionlogin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class EmergenciaAdapter(private val lista: List<Emergencia>) :
    RecyclerView.Adapter<EmergenciaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mensaje: TextView = view.findViewById(R.id.mensajeText)
        val fecha: TextView = view.findViewById(R.id.fechaText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_emergencia, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emergencia = lista[position]
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val fechaFormateada = emergencia.timestamp?.let { formato.format(it) } ?: "Sin fecha"

        holder.mensaje.text = emergencia.mensaje
        holder.fecha.text = fechaFormateada
    }
}
