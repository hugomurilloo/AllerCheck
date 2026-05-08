package com.allercheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val registeredUsers = mutableListOf<User>()

    // ESTATS PRIVATS
    private val _name = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _confirmPassword = MutableLiveData<String>() // Nou

    private val _nameError = MutableLiveData<String?>()
    private val _emailError = MutableLiveData<String?>()
    private val _passwordError = MutableLiveData<String?>()
    private val _confirmPasswordError = MutableLiveData<String?>() // Nou

    private val _registerSuccess = MutableLiveData<Boolean>()
    private val _registerError = MutableLiveData<String?>()
    private val _isFormValid = MutableLiveData<Boolean>()

    // ESTATS PÚBLICS
    val nameError: LiveData<String?> = _nameError
    val emailError: LiveData<String?> = _emailError
    val passwordError: LiveData<String?> = _passwordError
    val confirmPasswordError: LiveData<String?> = _confirmPasswordError
    val registerSuccess: LiveData<Boolean> = _registerSuccess
    val registerError: LiveData<String?> = _registerError
    val isFormValid: LiveData<Boolean> = _isFormValid

    init {
        _isFormValid.value = false
        _registerSuccess.value = false
    }

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

    fun onConfirmPasswordChanged(password: String) {
        _confirmPassword.value = password
        validateForm()
    }

    private fun validateForm() {
        val nameVal = _name.value ?: ""
        val emailVal = _email.value ?: ""
        val passVal = _password.value ?: ""
        val confVal = _confirmPassword.value ?: ""

        val nameValid = nameVal.trim().isNotEmpty()
        val emailValid = emailVal.contains("@") && emailVal.isNotBlank()
        val passwordValid = passVal.length >= 8
        val passwordsMatch = passVal == confVal && confVal.isNotEmpty()

        _nameError.value = if (!nameValid && nameVal.isNotEmpty()) "El nom no pot ser buit" else null
        _emailError.value = if (!emailValid && emailVal.isNotEmpty()) "L'email no és vàlid" else null
        _passwordError.value = if (!passwordValid && passVal.isNotEmpty()) "Mínim 8 caràcters" else null
        _confirmPasswordError.value = if (!passwordsMatch && confVal.isNotEmpty()) "Les contrasenyes no coincideixen" else null

        _isFormValid.value = nameValid && emailValid && passwordValid && passwordsMatch
    }

    fun register() {
        if (_isFormValid.value == true) {
            val newUser = User(_name.value!!, _email.value!!, _password.value!!)
            registeredUsers.add(newUser)
            _registerSuccess.value = true
        }
    }
}