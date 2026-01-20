package com.allercheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    // Lista usuarios
    private  val registeredUsers = mutableListOf<User>()

    //Estados UI
    private val _name = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _nameError = MutableLiveData<String>()
    private val _emailError = MutableLiveData<String>()
    private val _passwordError = MutableLiveData<String>()
    private val _registerSuccess = MutableLiveData<Boolean>()
    private val _registerError = MutableLiveData<String>()
    private val _isFormValid = MutableLiveData<Boolean>()

    //Estados UI publicos
    val name: LiveData<String> = _name
    val email: LiveData<String> = _email
    val passsword: LiveData<String> = _password
    val nameError: LiveData<String> = _nameError
    val emailError: LiveData<String> = _emailError
    val passwordError: LiveData<String> = _passwordError
    val registerSuccess: LiveData<Boolean> = _registerSuccess
    val registerError: LiveData<String> = _registerError
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

    private  fun validateForm() {
        val nameValid = isValidName(_name.value ?: "")
        val emailValid = isEmailValid(_email.value ?: "")
        val passwordValid = isPasswordValid(_password.value ?: "")

        _nameError.value = if (!nameValid) "El nom no pot ser null" else null
        _emailError.value = if (!emailValid) "El email no pot ser null" else null
        _passwordError.value = if (!passwordValid) "Minim 8 caracters" else null

        _isFormValid.value = nameValid && passwordValid && emailValid

    }

    private fun isValidName(name: String): Boolean = name.trim().isNotEmpty()
    private fun isEmailValid(email: String): Boolean = email.trim().isNotEmpty()
    private fun isPasswordValid(password: String): Boolean = password.length >= 8

    fun register(){
        if(_isFormValid.value == true){
            val name = _name.value ?: ""
            val email = _email.value ?: ""
            val password = _password.value ?: ""

            //Verificar si el email ya esta creado
            val emailExists = registeredUsers.any{it.email == email}

            if(emailExists){
                _registerSuccess.value = false
                _registerError.value = "Email ja registrat"
            } else{
                //Guardamos el usuario
                val newUser = User(name,email,password)
                registeredUsers.add(newUser)

                _registerSuccess.value = true
                _registerError.value = null
                println("Usuari registrat: $email ")
                println("Usuaris registrats: ${registeredUsers.size}")

                
            }
        }
    }
    fun verifyLogin(email: String, password: String): Boolean{
        return registeredUsers.any{it.email == email && it.password == password}
    }




}