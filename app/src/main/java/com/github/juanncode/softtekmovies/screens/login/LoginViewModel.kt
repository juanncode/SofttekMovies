package com.github.juanncode.softtekmovies.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.juanncode.domain.UserDataValidator
import com.github.juanncode.domain.utils.ErrorGeneric
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
) : ViewModel() {
    var state by mutableStateOf(LoginState())

    init {
        validateEmail()
        validatePassword()
    }

    fun validatePassword() {
        snapshotFlow {
            state.password.text
        }.onEach { password ->
            println(password)
            println(state.email)
            val isValidPassword = userDataValidator.isValidPassword(password.toString())
            val isValidEmail = userDataValidator.isValidEmail(state.email.text.toString())
            state = state.copy(
                isPasswordValid = isValidPassword,
                canLogin = isValidEmail && isValidPassword
            )
        }.launchIn(viewModelScope)
    }

    fun validateEmail() {
        snapshotFlow {
            state.email.text
        }.onEach { email ->
            if (email.isEmpty()) return@onEach
            val isValidEmail = userDataValidator.isValidEmail(email.toString())

            state = state.copy(
                isEmailValid = isValidEmail,
                canLogin = isValidEmail && state.isPasswordValid
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLoginClick -> {
                login()
            }

            LoginEvent.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }

            LoginEvent.CleanError -> {
                state = state.copy(error = null)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            delay(1000)

            state = state.copy(isLoggingIn = false)
            state = if (state.email.text == "example@test.es" && state.password.text == "123")
                state.copy(success = true)
            else
                state.copy(
                    error = ErrorGeneric(
                        error = "Correo o contraseña incorrectos",
                        code = 0,
                        userMessage = "Correo o contraseña incorrectos"
                    )
                )

        }
    }
}