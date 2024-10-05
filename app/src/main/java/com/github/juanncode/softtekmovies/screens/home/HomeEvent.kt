package com.github.juanncode.softtekmovies.screens.home

sealed interface HomeEvent {
    data object CleanError: HomeEvent
}