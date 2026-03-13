package com.allercheck

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class activity_create_edit_review : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var tvTitulo: TextView
    private lateinit var reviewEditText: TextInputEditText
    private lateinit var csR1: CheckBox
    private lateinit var rgEstrellas: RadioGroup

    private var existingReview: Ressenya? = null
    private var restaurantId: String = "1"
    private var restaurantName: String = "Restaurant"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_review)

        // Inicializar vistas
        btnAtras = findViewById(R.id.btnAtras)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
        tvTitulo = findViewById(R.id.tvTitulo)
        reviewEditText = findViewById(R.id.text_input_edit_text)
        csR1 = findViewById(R.id.csR1)
        rgEstrellas = findViewById(R.id.rgEstrellas)
        if (intent.hasExtra("EXTRA_REVIEW")) {
            existingReview = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("EXTRA_REVIEW", Ressenya::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra<Ressenya>("EXTRA_REVIEW")
            }
        }

        // Datos del restaurante
        restaurantId = intent.getStringExtra("REST_ID") ?: "1"
        restaurantName = intent.getStringExtra("REST_NAME") ?: "Restaurant"

        if (existingReview != null) {
            tvTitulo.text = getString(R.string.editar_ressenya)
            reviewEditText.setText(existingReview?.reviewText)
            csR1.isChecked = existingReview?.confirmed ?: false
            setRating(existingReview?.stars ?: 0)
            btnEliminar.visibility = View.VISIBLE

              restaurantId = existingReview!!.restaurantId
            restaurantName = existingReview!!.restaurantName
        } else {
            // MODO NUEVA RESEÑA
            tvTitulo.text = getString(R.string.nova_ressenya)
            btnEliminar.visibility = View.GONE
        }

        setupListeners()
    }

    private fun setupListeners() {
        btnAtras.setOnClickListener { finish() }
        btnGuardar.setOnClickListener { saveReviewToApi() }
        btnEliminar.setOnClickListener { deleteReviewFromApi() }
    }

    private fun saveReviewToApi() {
        val stars = getRating()
        val text = reviewEditText.text.toString()
        val confirmed = csR1.isChecked

        if (text.isBlank()) {
            Toast.makeText(this, "Escriu un comentari abans de guardar", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = if (existingReview == null) {
                    val newReview = Ressenya("0", restaurantId, restaurantName, stars, text, confirmed)
                    ItemAPI.API().createReview(newReview)
                } else {
                    val updated = existingReview!!.copy(stars = stars, reviewText = text, confirmed = confirmed)
                    ItemAPI.API().updateReview(updated.id, updated)
                }

                if (response.isSuccessful) {
                    Toast.makeText(this@activity_create_edit_review, "Fet!", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@activity_create_edit_review, "Error de connexió", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteReviewFromApi() {
        existingReview?.let { review ->
            lifecycleScope.launch {
                try {
                    val response = ItemAPI.API().deleteReview(review.id)
                    if (response.isSuccessful) {
                        Toast.makeText(this@activity_create_edit_review, "Eliminada", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@activity_create_edit_review, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getRating(): Int {
        return when (rgEstrellas.checkedRadioButtonId) {
            R.id.rbE1 -> 1; R.id.rbE2 -> 2; R.id.rbE3 -> 3; R.id.rbE4 -> 4; R.id.rbE5 -> 5
            else -> 0
        }
    }

    private fun setRating(rating: Int) {
        val id = when (rating) {
            1 -> R.id.rbE1; 2 -> R.id.rbE2; 3 -> R.id.rbE3; 4 -> R.id.rbE4; 5 -> R.id.rbE5
            else -> -1
        }
        if (id != -1) rgEstrellas.check(id)
    }
}