package com.allercheck

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    // Regla obligatòria per testejar LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        // Inicialitzem el ViewModel net abans de cada test
        viewModel = RegisterViewModel()
    }

    @Test // Prova 1: Nom Buit
    fun onNameChanged_buit_mostraError() {
        // Simulem que l'usuari entra un nom i després el deixa buit
        viewModel.onNameChanged("H")
        viewModel.onNameChanged("")

        // Si està buit l'error és null però el form és invàlid
        assertEquals(null, viewModel.nameError.value)
        assertFalse(viewModel.isFormValid.value!!)
    }

    @Test // Prova 2: Email Incorrecte
    fun onEmailChanged_incorrecte_mostraError() {
        viewModel.onEmailChanged("usuari.gmail.com") // Sense @
        assertEquals("L'email no és vàlid", viewModel.emailError.value)
        assertFalse(viewModel.isFormValid.value!!)
    }

    @Test // Prova 3: Contrasenya Curta
    fun onPasswordChanged_curta_mostraError() {
        viewModel.onPasswordChanged("12345") // Menys de 8
        assertEquals("Mínim 8 caràcters", viewModel.passwordError.value)
        assertFalse(viewModel.isFormValid.value!!)
    }

    @Test // Contrasenyes no coincideixen (Requisit del 10)
    fun confirmPassword_noCoincideix_mostraError() {
        viewModel.onPasswordChanged("password123")
        viewModel.onConfirmPasswordChanged("password456") // Diferent

        assertEquals("Les contrasenyes no coincideixen", viewModel.confirmPasswordError.value)
        assertFalse(viewModel.isFormValid.value!!)
    }

    @Test // Prova 4: Tot Correcte (Camí normal)
    fun formulari_valid_activaBoto() {
        viewModel.onNameChanged("Hugo")
        viewModel.onEmailChanged("hugo@test.com")
        viewModel.onPasswordChanged("password123")
        viewModel.onConfirmPasswordChanged("password123")

        // Verifiquem que no hi ha errors i el botó s'activa
        assertNull(viewModel.nameError.value)
        assertNull(viewModel.emailError.value)
        assertNull(viewModel.passwordError.value)
        assertNull(viewModel.confirmPasswordError.value)

        assertTrue(viewModel.isFormValid.value!!)
    }

    @Test // Prova 5: Execució Registre
    fun register_dadesCorrectes_guardaUsuari() {
        viewModel.onNameChanged("Hugo")
        viewModel.onEmailChanged("hugo@test.com")
        viewModel.onPasswordChanged("password123")
        viewModel.onConfirmPasswordChanged("password123")

        viewModel.register()

        // Verifiquem que el LiveData de success és true
        assertTrue(viewModel.registerSuccess.value!!)
    }
}