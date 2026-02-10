package com.allercheck

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class activity_create_edit_review : AppCompatActivity() {

    private lateinit var btnAtras: ImageButton
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var tvTitulo: TextView
    private lateinit var reviewEditText: TextInputEditText
    private lateinit var csR1: CheckBox
    private lateinit var rgEstrellas: RadioGroup

    private var currentRating = 0
    private var existingReview: Ressenya? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_review)

        // INICIALIZAR VISTAS
        btnAtras = findViewById(R.id.btnAtras)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
        tvTitulo = findViewById(R.id.tvTitulo)
        reviewEditText = findViewById(R.id.text_input_edit_text)
        csR1 = findViewById(R.id.csR1)
        rgEstrellas = findViewById(R.id.rgEstrellas)

        if (intent.hasExtra("EXTRA_REVIEW")) {
            existingReview = intent.getParcelableExtra("EXTRA_REVIEW")
        }

        if (existingReview != null) {
            tvTitulo.text = getString(R.string.editar_ressenya)
            reviewEditText.setText(existingReview!!.reviewText)
            csR1.isChecked = existingReview!!.confirmed
            setRating(existingReview!!.stars)
            btnEliminar.visibility = View.VISIBLE
        } else {
            tvTitulo.text = getString(R.string.nova_ressenya)
            btnEliminar.visibility = View.GONE
        }

        // LISTENERS
        setupListeners()
    }

    private fun setupListeners() {
        btnAtras.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        btnGuardar.setOnClickListener {
            val rating = getRating()
            setResult(Activity.RESULT_OK)
            finish()
        }

        btnEliminar.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun getRating(): Int {
        return when (rgEstrellas.checkedRadioButtonId) {
            R.id.rbE1 -> 1
            R.id.rbE2 -> 2
            R.id.rbE3 -> 3
            R.id.rbE4 -> 4
            R.id.rbE5 -> 5
            else -> 0
        }
    }

    private fun setRating(rating: Int) {
        val radioButtonId = when (rating) {
            1 -> R.id.rbE1
            2 -> R.id.rbE2
            3 -> R.id.rbE3
            4 -> R.id.rbE4
            5 -> R.id.rbE5
            else -> -1
        }
        if (radioButtonId != -1) {
            rgEstrellas.check(radioButtonId)
        } else {
            rgEstrellas.clearCheck()
        }
    }
}
