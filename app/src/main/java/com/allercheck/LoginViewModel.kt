package com.allercheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    // ESTADOS PRIVADOS
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _loginSuccess = MutableLiveData<Boolean>()
    private val _loginError = MutableLiveData<String?>()
    private val _isFormValid = MutableLiveData<Boolean>()
    private  val _emailError = MutableLiveData<String?>()
    private  val _passwordError = MutableLiveData<String?>()

    // ESTADOS PUBLICOS
    val loginSuccess: LiveData<Boolean> = _loginSuccess
    val loginError: LiveData<String?> = _loginError
    val isFormValid: LiveData<Boolean> = _isFormValid
    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val emailError: LiveData<String?> = _emailError
    val passwordError: LiveData<String?> = _passwordError

    init {
        _isFormValid.value = false
        _loginSuccess.value = false
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        validateForm()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        validateForm()
    }

    private fun isEmailValid(email: String): Boolean = email.isNotBlank() && email.contains("@")
    private fun isPasswordValid(password: String): Boolean = password.length >= 8

    private fun validateForm() {
        val emailValid = isEmailValid(_email.value ?: "")
        val passwordValid = isPasswordValid(_password.value ?: "")

        _emailError.value = if (!emailValid) "L'email no és vàlid" else null
        _passwordError.value = if (!passwordValid) "Mínim 8 caràcters" else null

        _isFormValid.value = emailValid && passwordValid
    }

    fun onLoginClicked() {

        if (_isFormValid.value == true) {
            _loginSuccess.value = true
            _loginError.value = null
        } else {
            _loginSuccess.value = false

        }
    }
}
