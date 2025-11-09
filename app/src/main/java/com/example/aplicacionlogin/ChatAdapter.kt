package com.example.aplicacionlogin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val mensajes: List<Mensaje>) : 
    RecyclerView.Adapter<ChatAdapter.MensajeViewHolder>() {

    class MensajeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val mensajeText: TextView = view.findViewById(R.id.mensajeText)
        val mensajeLayout: LinearLayout = view.findViewById(R.id.mensajeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mensaje, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = mensajes[position]
        holder.mensajeText.text = mensaje.texto

        // Detectar si es el mensaje especial de "no hay respuesta"
        val esMensajeEspecial = mensaje.esBot && mensaje.texto.contains("+57 350 774 4821")

        if (mensaje.esBot) {
            holder.mensajeLayout.background = 
                ContextCompat.getDrawable(holder.view.context, R.drawable.chat_bot_message_bg)
            holder.mensajeLayout.layoutParams = 
                (holder.mensajeLayout.layoutParams as FrameLayout.LayoutParams).apply {
                    gravity = Gravity.START
                    marginStart = 16
                    marginEnd = 64
                }
            if (esMensajeEspecial) {
                holder.mensajeText.setTextColor(Color.parseColor("#D32F2F")) // Rojo llamativo
                holder.mensajeText.setTypeface(null, Typeface.BOLD)
                holder.mensajeText.textSize = 18f
            } else {
                holder.mensajeText.setTextColor(Color.parseColor("#212121"))
                holder.mensajeText.setTypeface(null, Typeface.NORMAL)
                holder.mensajeText.textSize = 16f
            }
        } else {
            holder.mensajeLayout.background = 
                ContextCompat.getDrawable(holder.view.context, R.drawable.chat_message_bg)
            holder.mensajeLayout.layoutParams = 
                (holder.mensajeLayout.layoutParams as FrameLayout.LayoutParams).apply {
                    gravity = Gravity.END
                    marginStart = 64
                    marginEnd = 16
                }
            holder.mensajeText.setTextColor(Color.parseColor("#212121"))
            holder.mensajeText.setTypeface(null, Typeface.NORMAL)
            holder.mensajeText.textSize = 16f
        }
    }

    override fun getItemCount() = mensajes.size

    override fun getItemViewType(position: Int): Int {
        return if (mensajes[position].esBot) VIEW_TYPE_BOT else VIEW_TYPE_USER
    }

    companion object {
        private const val VIEW_TYPE_BOT = 1
        private const val VIEW_TYPE_USER = 2
    }
}