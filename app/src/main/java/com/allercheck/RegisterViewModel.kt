package com.allercheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    // Lista usuarios
    private val registeredUsers = mutableListOf<User>()

    // ESTADOS PRIVADOS
    private val _name = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _nameError = MutableLiveData<String?>()
    private val _emailError = MutableLiveData<String?>()
    private val _passwordError = MutableLiveData<String?>()
    private val _registerSuccess = MutableLiveData<Boolean>()
    private val _registerError = MutableLiveData<String?>()
    private val _isFormValid = MutableLiveData<Boolean>()

    // ESTADOS PUBLICOS
    val name: LiveData<String> = _name
    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val nameError: LiveData<String?> = _nameError
    val emailError: LiveData<String?> = _emailError
    val passwordError: LiveData<String?> = _passwordError
    val registerSuccess: LiveData<Boolean> = _registerSuccess
    val registerError: LiveData<String?> = _registerError
    val isFormValid: LiveData<Boolean> = _isFormValid

    init {
        _isFormValid.value = false
        _registerSuccess.value = false
    }

    // Metodos para el View
    fun onNameChanged(name: String) {
        _name.value = name
        validateForm()
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        validateForm()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        validateForm()
    }

    private fun validateForm() {
        val nameValid = isValidName(_name.value ?: "")
        val emailValid = isEmailValid(_email.value ?: "")
        val passwordValid = isPasswordValid(_password.value ?: "")
        val emailExists = registeredUsers.any { it.email == _email.value }

        _nameError.value = if (!nameValid) "El nom no pot ser buit" else null
        _emailError.value = if (!emailValid) {
            "L'email no és vàlid"
        } else if (emailExists) {
            "Aquest email ja està en ús"
        } else {
            null
        }
        _passwordError.value = if (!passwordValid) "Mínim 8 caràcters" else null

        _isFormValid.value = nameValid && emailValid && passwordValid && !emailExists
    }

    private fun isValidName(name: String): Boolean = name.trim().isNotEmpty()
    private fun isEmailValid(email: String): Boolean = email.isNotBlank() && email.contains("@")
    private fun isPasswordValid(password: String): Boolean = password.length >= 8

    // METODO PARA CREAR LOS USUARIOS
    fun register() {
        if (_isFormValid.value == true) {
            val name = _name.value!!
            val email = _email.value!!
            val password = _password.value!!

            val newUser = User(name, email, password)
            registeredUsers.add(newUser)

            _registerSuccess.value = true
            _registerError.value = null

        } else {
            _registerSuccess.value = false
            _registerError.value = "Si us plau, corregeix els errors."
        }
    }
}
