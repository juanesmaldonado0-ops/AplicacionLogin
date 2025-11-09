package com.example.aplicacionlogin

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AsistenteActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var db: FirebaseFirestore
    private val mensajes = mutableListOf<Mensaje>()
    private lateinit var adapter: ChatAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistente)

        // Inicializar Firebase
        db = FirebaseFirestore.getInstance()

        // Inicializar vistas
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        // Configurar toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Configurar RecyclerView
        adapter = ChatAdapter(mensajes)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerView.adapter = adapter

        // Configurar input
        messageInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                enviarMensaje()
                true
            } else {
                false
            }
        }

        // Configurar botón de enviar
        sendButton.setOnClickListener {
            enviarMensaje()
        }

        // Mensaje inicial
        mostrarMensajeBienvenida()
    }

    private fun enviarMensaje() {
        val pregunta = messageInput.text.toString().trim()
        if (pregunta.isNotEmpty()) {
            enviarPregunta(pregunta)
            messageInput.text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(messageInput.windowToken, 0)
    }

    private fun mostrarMensajeBienvenida() {
        val mensaje = Mensaje(
            "¡Hola! Soy tu asistente virtual. ¿En qué puedo ayudarte?",
            true
        )
        mensajes.add(mensaje)
        adapter.notifyItemInserted(mensajes.size - 1)
    }

    private fun enviarPregunta(pregunta: String) {
        // Agregar pregunta del usuario al chat
        mensajes.add(Mensaje(pregunta, false))
        adapter.notifyItemInserted(mensajes.size - 1)

        // Buscar respuesta en la base de conocimientos
        buscarRespuesta(pregunta)
    }

    private fun buscarRespuesta(pregunta: String) {
        db.collection("preguntas_frecuentes")
            .get()
            .addOnSuccessListener { documentos ->
                var mejorRespuesta = "Lo siento, no encontré una respuesta específica. Si necesitas ayuda urgente puedes comunicarte al número +57 350 774 4821. ¿Podrías reformular tu pregunta?"
                var mejorPuntuacion = 0.0

                for (documento in documentos) {
                    val preguntaGuardada = documento.getString("pregunta") ?: continue
                    val respuestaGuardada = documento.getString("respuesta") ?: continue
                    
                    val puntuacion = calcularSimilitud(pregunta.lowercase(), preguntaGuardada.lowercase())
                    if (puntuacion > mejorPuntuacion && puntuacion > 0.5) {
                        mejorPuntuacion = puntuacion
                        mejorRespuesta = respuestaGuardada
                    }
                }

                // Mostrar la respuesta
                mensajes.add(Mensaje(mejorRespuesta, true))
                adapter.notifyItemInserted(mensajes.size - 1)
                recyclerView.smoothScrollToPosition(mensajes.size - 1)
            }
    }

    private fun calcularSimilitud(texto1: String, texto2: String): Double {
        // Implementación simple de similitud basada en palabras comunes
        val palabras1 = texto1.split(" ").toSet()
        val palabras2 = texto2.split(" ").toSet()
        val interseccion = palabras1.intersect(palabras2).size
        val union = palabras1.union(palabras2).size
        return interseccion.toDouble() / union.toDouble()
    }
}

data class Mensaje(
    val texto: String,
    val esBot: Boolean
)