package com.allercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

class activity_login : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var btnAccedir: Button
    private lateinit var btnRegistrar: Button
    private lateinit var btnAtras: ImageButton
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnAccedir = findViewById(R.id.accedir)
        btnRegistrar = findViewById(R.id.registrar)
        btnAtras = findViewById(R.id.btnAtras)
        emailInputLayout = findViewById(R.id.email_input)
        passwordInputLayout = findViewById(R.id.contrasenya_input)

        emailInputLayout.editText?.doOnTextChanged { text, _, _, _ ->
            loginViewModel.onEmailChanged(text.toString())
        }
        passwordInputLayout.editText?.doOnTextChanged { text, _, _, _ ->
            loginViewModel.onPasswordChanged(text.toString())
        }

        setupObservers()


        btnAccedir.setOnClickListener {
            loginViewModel.onLoginClicked()
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, activity_register::class.java)
            startActivity(intent)
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        loginViewModel.isFormValid.observe(this) { isEnabled ->
            btnAccedir.isEnabled = isEnabled
        }

        loginViewModel.emailError.observe(this) { error ->
            emailInputLayout.error = error
        }

        loginViewModel.passwordError.observe(this) { error ->
            passwordInputLayout.error = error
        }

        loginViewModel.loginSuccess.observe(this) { hasSucceeded ->
            if (hasSucceeded) {
                val intent = Intent(this, activity_config_restrictions::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.loginError.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
