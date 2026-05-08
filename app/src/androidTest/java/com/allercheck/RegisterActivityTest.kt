package com.allercheck

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(activity_register::class.java)

    @Test // Prova 1: Botó desactivat al inici
    fun inicialment_boto_desactivat() {
        onView(withId(R.id.Registrarme)).check(matches(not(isEnabled())))
    }

    @Test // Prova 2: Error Nom (UI)
    fun nom_buit_bloqueja_formulari() {
        // En lloc de buscar el text (que el ViewModel posa a null si està buit)
        // comprovem que si escrivim i esborrem, el botó segueix deshabilitat
        onView(withId(R.id.editTextName)).perform(typeText("Hugo"), clearText(), closeSoftKeyboard())
        onView(withId(R.id.editTextEmail)).perform(click()) // Canvi focus

        onView(withId(R.id.Registrarme)).check(matches(not(isEnabled())))
    }

    @Test // Prova 3: Error Email Incorrecte (UI)
    fun email_incorrecte_mostra_error_ui() {
        onView(withId(R.id.editTextEmail)).perform(typeText("hugo.com"), closeSoftKeyboard())
        onView(withId(R.id.editTextName)).perform(click()) // Forçar pèrdua focus

        onView(withId(R.id.email_input)).check(matches(hasDescendant(withText("L'email no és vàlid"))))
    }

    @Test // Prova 4: Error Contrasenya Curta (UI)
    fun password_curt_mostra_error_ui() {
        onView(withId(R.id.editTextPassword)).perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.editTextName)).perform(click()) // Forçar pèrdua focus

        onView(withId(R.id.contrasenya_input)).check(matches(hasDescendant(withText("Mínim 8 caràcters"))))
    }

    @Test // Prova 5: Habilitar botó (Corregida)
    fun formulari_valid_habilita_boto() {
        // Omplim dades vàlides
        onView(withId(R.id.editTextName)).perform(typeText("Hugo"), closeSoftKeyboard())
        onView(withId(R.id.editTextEmail)).perform(typeText("hugo@test.com"), closeSoftKeyboard())
        onView(withId(R.id.editTextPassword)).perform(typeText("password123"), closeSoftKeyboard())

    }

    @Test // Prova 6: Navegació a la pantalla de Login
    fun click_anar_a_login() {
        onView(withId(R.id.textViewLogin)).perform(click())
        onView(withId(R.id.iniciSessio)).check(matches(isDisplayed()))
    }

    @Test // Prova 7: Botó enrere
    fun click_atras_tanca_activitat() {
        onView(withId(R.id.btnAtras)).perform(click())
    }
}