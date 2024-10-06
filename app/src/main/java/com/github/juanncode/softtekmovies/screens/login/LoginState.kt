package com.github.juanncode.softtekmovies.screens.login

import androidx.compose.foundation.text.input.TextFieldState
import com.github.juanncode.domain.utils.ErrorGeneric

data class LoginState(

    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
    val error: ErrorGeneric? = null,
    val success: Boolean = false,
)