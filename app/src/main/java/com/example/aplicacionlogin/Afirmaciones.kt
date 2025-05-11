package com.example.aplicacionlogin

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class Afirmaciones : AppCompatActivity() {

    private lateinit var tvFrase: TextView
    private lateinit var btnCategoria: Button
    private lateinit var favButton: Button
    private lateinit var btnCerrar: ImageButton

    private val frases = mutableListOf<String>()
    private var indiceActual = 0
    private var esFavorito = false
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afirmaciones)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCategoria = findViewById(R.id.btnCategoria)
        tvFrase = findViewById(R.id.tvFrase)
        favButton = findViewById(R.id.favButton)
        btnCerrar = findViewById(R.id.btnCerrar)  // Inicializar el botón de cerrar

        // Obtener la categoría enviada desde la pantalla anterior
        val categoria = intent.getStringExtra("categoria") ?: "General"
        btnCategoria.text = categoria

        // Cargar frases desde Firestore para la categoría seleccionada
        cargarFrasesDesdeFirestore(categoria)

        // Verificar si la categoría "Tus favoritos" tiene frases antes de permitir el acceso
        if (categoria == "Tus favoritos") {
            verificarFavoritosNoVacios()
        }

        // Redirigir a pantalla de categorías al hacer clic
        btnCategoria.setOnClickListener {
            val intent = Intent(this, categorias::class.java)
            startActivity(intent)
        }

        // Mostrar siguiente frase al tocar el texto
        tvFrase.setOnClickListener {
            if (frases.isNotEmpty()) {
                indiceActual = (indiceActual + 1) % frases.size
                tvFrase.text = frases[indiceActual]
                verificarSiEsFavorito(frases[indiceActual])
            }
        }

        // Marcar o desmarcar como favorito al presionar el botón
        favButton.setOnClickListener {
            if (frases.isNotEmpty()) {
                val fraseActual = frases[indiceActual]
                if (esFavorito) {
                    eliminarDeFavoritos(fraseActual)
                } else {
                    agregarAFavoritos(fraseActual)
                }
            }
        }

        // Redirigir a la actividad biblioteca al presionar el botón de cerrar
        btnCerrar.setOnClickListener {
            val intent = Intent(this, biblioteca::class.java)
            startActivity(intent)
            finish() // Opcional, si deseas que al cerrar la actividad actual no puedas volver con el botón atrás
        }
    }

    // Función para cargar las frases desde Firestore
    private fun cargarFrasesDesdeFirestore(categoria: String) {
        db.collection("frases")
            .document(categoria)
            .collection("lista")
            .get()
            .addOnSuccessListener { result ->
                frases.clear()
                for (document in result) {
                    val texto = document.getString("texto")
                    if (!texto.isNullOrEmpty()) {
                        frases.add(texto)
                    }
                }
                if (frases.isNotEmpty()) {
                    indiceActual = 0
                    tvFrase.text = frases[0]
                    verificarSiEsFavorito(frases[0])
                } else {
                    // Si la categoría no tiene frases, muestra un mensaje, pero no cambia el texto del botón
                    Toast.makeText(this, "No hay frases en esta categoría", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar frases", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para verificar si la frase es un favorito
    private fun verificarSiEsFavorito(frase: String) {
        db.collection("frases")
            .document("Tus favoritos")
            .collection("lista")
            .whereEqualTo("texto", frase)
            .get()
            .addOnSuccessListener { documentos ->
                esFavorito = !documentos.isEmpty
                actualizarIconoFavorito()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al verificar favorito", Toast.LENGTH_SHORT).show()
            }
    }

    // Actualizar el icono del botón de favoritos
    private fun actualizarIconoFavorito() {
        val iconoId = if (esFavorito) R.drawable.favorito else R.drawable.corazon
        val icono = getDrawableRedimensionado(iconoId, 24) // 24dp es el tamaño que quieres
        favButton.setCompoundDrawables(icono, null, null, null)
    }

    // Función para redimensionar un drawable
    private fun getDrawableRedimensionado(drawableId: Int, sizeDp: Int): Drawable {
        val drawable = getDrawable(drawableId)!!
        val sizePx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, sizeDp.toFloat(), resources.displayMetrics
        ).toInt()
        drawable.setBounds(0, 0, sizePx, sizePx)
        return drawable
    }

    // Función para agregar a favoritos
    private fun agregarAFavoritos(frase: String) {
        val nuevaFrase = hashMapOf("texto" to frase)
        db.collection("frases")
            .document("Tus favoritos")
            .collection("lista")
            .add(nuevaFrase)
            .addOnSuccessListener {
                esFavorito = true
                actualizarIconoFavorito()
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para eliminar de favoritos
    private fun eliminarDeFavoritos(frase: String) {
        db.collection("frases")
            .document("Tus favoritos")
            .collection("lista")
            .whereEqualTo("texto", frase)
            .get()
            .addOnSuccessListener { documentos ->
                for (doc in documentos) {
                    db.collection("frases")
                        .document("Tus favoritos")
                        .collection("lista")
                        .document(doc.id)
                        .delete()
                }
                esFavorito = false
                actualizarIconoFavorito()
                Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar de favoritos", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para verificar si "Tus favoritos" tiene frases
    private fun verificarFavoritosNoVacios() {
        db.collection("frases")
            .document("Tus favoritos")
            .collection("lista")
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    // No cambiar el texto del botón y mostrar mensaje de error
                    Toast.makeText(this, "No tienes frases guardadas en favoritos", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al verificar favoritos", Toast.LENGTH_SHORT).show()
            }
    }
}
