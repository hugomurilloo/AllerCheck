package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.allercheck.databinding.ActivityRegisterBinding

class activity_register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    // OBSERVADORES
    private fun setupObservers() {
        viewModel.nameError.observe(this) { errorMessage ->
            binding.nomInput.error = errorMessage
        }

        viewModel.emailError.observe(this) { errorMessage ->
            binding.emailInput.error = errorMessage
        }

        viewModel.passwordError.observe(this) { errorMessage ->
            binding.contrasenyaInput.error = errorMessage
        }

        viewModel.isFormValid.observe(this) { isValid ->
            binding.Registrarme.isEnabled = isValid
        }

        viewModel.registerSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Registrat amb exit!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, activity_config_restrictions::class.java))
                finish()
            }
        }

        // EMAIL DUPLICADO
        viewModel.registerError.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    // LISTENERS
    private fun setupListeners() {
        binding.editTextName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.onNameChanged(binding.editTextName.text.toString())
            }
        }

        binding.editTextEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.onEmailChanged(binding.editTextEmail.text.toString())
            }
        }

        binding.editTextPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.onPasswordChanged(binding.editTextPassword.text.toString())
            }
        }

        // Botón REGISTRAR-ME
        binding.Registrarme.setOnClickListener {
            viewModel.onNameChanged(binding.editTextName.text.toString())
            viewModel.onEmailChanged(binding.editTextEmail.text.toString())
            viewModel.onPasswordChanged(binding.editTextPassword.text.toString())

            viewModel.register()

        }

        // Botón ATRÁS
        binding.btnAtras.setOnClickListener {
            finish()
        }

        // Login
        binding.textViewLogin.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }


        }
    }