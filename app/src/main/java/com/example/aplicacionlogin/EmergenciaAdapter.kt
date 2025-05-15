package com.example.aplicacionlogin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class EmergenciaAdapter(private val emergencias: List<Emergencia>) :
    RecyclerView.Adapter<EmergenciaAdapter.EmergenciaViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    // Clase ViewHolder que contiene las vistas
    class EmergenciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvMensaje: TextView = itemView.findViewById(R.id.tvMensaje)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergenciaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_emergencia, parent, false)
        return EmergenciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmergenciaViewHolder, position: Int) {
        val emergencia = emergencias[position]
        holder.tvFecha.text = dateFormat.format(emergencia.timestamp)
        holder.tvMensaje.text = emergencia.mensaje
    }

    // Método requerido: Retornar el número de ítems
    override fun getItemCount(): Int = emergencias.size
}