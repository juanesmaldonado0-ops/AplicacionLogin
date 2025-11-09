package com.example.aplicacionlogin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.view.WindowManager
import com.google.firebase.firestore.FirebaseFirestore

class AsistenteBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private val mensajes = mutableListOf<Mensaje>()
    private lateinit var adapter: ChatAdapter
    private lateinit var db: FirebaseFirestore
    private val viewTag = "__FLOATING_CHAT_BUTTON__"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.Theme_Design_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_asistente_bottomsheet, container, false)

        recyclerView = root.findViewById(R.id.chatRecyclerView)
        messageInput = root.findViewById(R.id.messageInput)
        sendButton = root.findViewById(R.id.sendButton)

        adapter = ChatAdapter(mensajes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply { stackFromEnd = true }
        recyclerView.adapter = adapter

        mostrarMensajeBienvenida()

        sendButton.setOnClickListener { enviarMensaje() }
        messageInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                enviarMensaje()
                true
            } else false
        }

        return root
    }

    private fun mostrarMensajeBienvenida() {
        val mensaje = Mensaje("¡Hola! Soy tu asistente. ¿En qué puedo ayudarte?", true)
        mensajes.add(mensaje)
        adapter.notifyItemInserted(mensajes.size - 1)
    }

    private fun enviarMensaje() {
        val texto = messageInput.text.toString().trim()
        if (texto.isEmpty()) return
        mensajes.add(Mensaje(texto, false))
        adapter.notifyItemInserted(mensajes.size - 1)
        messageInput.text.clear()
        hideKeyboard()
        buscarRespuesta(texto)
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(messageInput.windowToken, 0)
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

                mensajes.add(Mensaje(mejorRespuesta, true))
                adapter.notifyItemInserted(mensajes.size - 1)
                recyclerView.smoothScrollToPosition(mensajes.size - 1)
            }
    }

    private fun calcularSimilitud(texto1: String, texto2: String): Double {
        val palabras1 = texto1.split(" ").toSet()
        val palabras2 = texto2.split(" ").toSet()
        val interseccion = palabras1.intersect(palabras2).size
        val union = palabras1.union(palabras2).size
        return if (union == 0) 0.0 else interseccion.toDouble() / union.toDouble()
    }

    override fun onDismiss(dialog: android.content.DialogInterface) {
        super.onDismiss(dialog)
        // Al cerrar, mostrar de nuevo el FAB si existe
        val activity = activity as? FragmentActivity ?: return
        val decor = activity.window?.decorView as? ViewGroup ?: return
        val fab = decor.findViewWithTag<View>(viewTag)
        fab?.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()

        // Ajustar comportamiento del BottomSheet: peek y altura expandida
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val bottom = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? ViewGroup
            ?: return

        val behavior = BottomSheetBehavior.from(bottom)

        // Alturas recomendadas: peek ~200dp, expanded ~65% pantalla
        val peekPx = dpToPx(200)
        val screenHeight = resources.displayMetrics.heightPixels
        val expandedHeight = (screenHeight * 0.65).toInt()

        // Aplicar height para estado expandido y peek
        bottom.layoutParams.height = expandedHeight
        bottom.requestLayout()

        behavior.peekHeight = peekPx
        behavior.isDraggable = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}