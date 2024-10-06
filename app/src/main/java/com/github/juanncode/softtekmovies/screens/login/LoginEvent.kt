package com.github.juanncode.softtekmovies.screens.login

sealed interface LoginEvent {
    data object OnTogglePasswordVisibilityClick: LoginEvent
    data object OnLoginClick: LoginEvent
    data object CleanError: LoginEvent

}