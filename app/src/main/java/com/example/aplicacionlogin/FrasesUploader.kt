package com.example.aplicacionlogin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class FrasesUploader : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("FrasesUploader", "Se ha ejecutado la actividad")

        // Datos: mapa de categoría -> lista de frases
        val frasesPorCategoria = mapOf(
            "General" to listOf(
                "Estoy en paz con lo que no puedo controlar.",
                "Cada día es una nueva oportunidad.",
                "Merezco amor, éxito y felicidad.",
                "Mi mente está tranquila y enfocada.",
                "Confío en el proceso de la vida.",
                "Estoy exactamente donde debo estar.",
                "Me libero del estrés innecesario.",
                "Cada decisión me lleva hacia mi bienestar.",
                "Elijo pensamientos positivos hoy.",
                "Estoy agradecido por este momento."
            ),
            "Vida y felicidad" to listOf(
                "Mi vida está llena de alegría.",
                "Disfruto cada momento con plenitud.",
                "La felicidad es una elección que hago cada día.",
                "Estoy rodeado de cosas que me hacen sonreír.",
                "Celebro las pequeñas victorias.",
                "La vida me sorprende con cosas buenas.",
                "Estoy en sintonía con mi propósito.",
                "Siento gratitud por las bendiciones diarias.",
                "Mi energía positiva atrae bienestar.",
                "Estoy abierto a la alegría y la abundancia."
            ),
            "Amor propio" to listOf(
                "Me amo y me acepto tal como soy.",
                "Soy suficiente exactamente como soy.",
                "Mi valor no depende de la opinión de los demás.",
                "Me trato con respeto y compasión.",
                "Mi cuerpo es mi hogar, lo cuido con amor.",
                "Soy digno de amor y respeto.",
                "Me perdono por mis errores.",
                "Estoy creciendo a mi propio ritmo.",
                "Confío en mí mismo.",
                "Soy mi prioridad hoy."
            ),
            "Positividad corporal" to listOf(
                "Agradezco lo que mi cuerpo hace por mí.",
                "Mi cuerpo es fuerte y valioso.",
                "No necesito compararme con nadie.",
                "Cada parte de mí merece amor.",
                "Mi belleza es única.",
                "Elijo vestirme para mí, no para los demás.",
                "Celebro la diversidad de cuerpos.",
                "Mi salud es más importante que los estándares sociales.",
                "Cuido mi cuerpo desde el amor, no desde la crítica.",
                "Merezco sentirme bien en mi piel."
            ),
            "Crecimiento personal" to listOf(
                "Estoy aprendiendo y creciendo cada día.",
                "El cambio es parte de mi evolución.",
                "Cada error es una lección valiosa.",
                "Mi potencial es ilimitado.",
                "Me esfuerzo por ser la mejor versión de mí.",
                "Acepto los desafíos como oportunidades.",
                "Confío en mi capacidad para mejorar.",
                "Estoy construyendo un futuro brillante.",
                "Sigo avanzando con determinación.",
                "Mi desarrollo personal es mi prioridad."
            ),
            "Agradecimiento" to listOf(
                "Gracias por un nuevo día.",
                "Aprecio lo que tengo en mi vida.",
                "Doy gracias incluso por los pequeños detalles.",
                "El agradecimiento transforma mi perspectiva.",
                "Tengo muchas razones para estar agradecido.",
                "Valoro a las personas que me rodean.",
                "Agradezco lo que ya he logrado.",
                "Elijo enfocarme en lo que sí tengo.",
                "Estoy rodeado de bendiciones.",
                "Gracias por todo lo que está por venir."
            ),
            "Tiempos difíciles" to listOf(
                "Esto también pasará.",
                "Estoy siendo fuerte incluso cuando no lo siento.",
                "Mis emociones son válidas.",
                "Me permito sentir y sanar.",
                "No necesito tener todas las respuestas ahora.",
                "Doy un paso a la vez.",
                "Pido ayuda cuando la necesito.",
                "Resisto, respiro y sigo adelante.",
                "Estoy haciendo lo mejor que puedo.",
                "Mi dolor no define mi destino."
            )
        )

        // Subir a Firestore
        for ((categoria, frases) in frasesPorCategoria) {
            val categoriaRef = db.collection("frases").document(categoria)

            for ((index, frase) in frases.withIndex()) {
                val fraseData = hashMapOf(
                    "texto" to frase,
                    "orden" to index
                )
                categoriaRef.collection("lista").add(fraseData)
                    .addOnSuccessListener {
                        Log.d("SubidaFrase", "✅ Frase subida en '$categoria': $frase")
                    }
                    .addOnFailureListener { e ->
                        Log.e("SubidaFrase", "❌ Error al subir frase: $frase", e)
                    }
            }
        }
    }
}
