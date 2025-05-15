package com.example.aplicacionlogin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.aplicacionlogin.databinding.ItemReflexionBinding

class ReflexionAdapter(private val reflexiones: List<reflexiones>) :
    RecyclerView.Adapter<ReflexionAdapter.ReflexionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReflexionViewHolder {
        val binding = ItemReflexionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReflexionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReflexionViewHolder, position: Int) {
        val reflexion = reflexiones[position]

    }

    override fun getItemCount(): Int = reflexiones.size

    class ReflexionViewHolder(val binding: ItemReflexionBinding) : RecyclerView.ViewHolder(binding.root)
}
