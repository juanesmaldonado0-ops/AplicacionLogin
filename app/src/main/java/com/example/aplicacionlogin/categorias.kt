package com.example.aplicacionlogin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class categorias : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var etSearch: EditText
    private lateinit var noResultsTextView: TextView
    private val allCategories = listOf(
        Category("General", R.string.general),
        Category("Vida y felicidad", R.string.vida),
        Category("Tus favoritos", R.string.favoritos),
        Category("Amor propio", R.string.amor_propio),
        Category("Positividad corporal", R.string.positividad),
        Category("Crecimiento personal", R.string.crecimiento),
        Category("Agradecimiento", R.string.agradecimiento),
        Category("Tiempos difíciles", R.string.dificil)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        val tvCancel = findViewById<TextView>(R.id.tvCancel)
        etSearch = findViewById(R.id.etSearch)
        gridLayout = findViewById(R.id.gridLayout)
        noResultsTextView = findViewById(R.id.noResultsTextView)

        renderCategories(allCategories)

        tvCancel.setOnClickListener {
            finish()
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()
                val filtered = allCategories.filter { category ->
                    getString(category.nameResId).lowercase().contains(query)
                }
                renderCategories(filtered)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun renderCategories(categories: List<Category>) {
        gridLayout.removeAllViews()

        if (categories.isEmpty()) {
            noResultsTextView.visibility = TextView.VISIBLE
        } else {
            noResultsTextView.visibility = TextView.GONE
            categories.forEach { category ->
                val cardView = createCategoryCard(category)
                cardView.setOnClickListener {
                    // Redirigir a la actividad Afirmaciones al hacer clic en cualquier categoría
                    val intent = Intent(this, Afirmaciones::class.java)
                    startActivity(intent)
                }
                gridLayout.addView(cardView)
            }
        }
    }

    private fun createCategoryCard(category: Category): CardView {
        val context = this

        val cardView = CardView(context).apply {
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 120.dpToPx(context)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8.dpToPx(context), 8.dpToPx(context), 8.dpToPx(context), 8.dpToPx(context))
            }
            radius = 24f
            cardElevation = 6f
            setCardBackgroundColor(0xFFFFFFFF.toInt())
        }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(16, 16, 16, 16)
        }

        val textView = TextView(context).apply {
            text = getString(category.nameResId)
            textSize = 14f
            gravity = Gravity.CENTER
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            setTextColor(0xFF000000.toInt())
        }

        layout.addView(textView)
        cardView.addView(layout)

        return cardView
    }

    private fun Int.dpToPx(context: android.content.Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    data class Category(val name: String, val nameResId: Int)
}
