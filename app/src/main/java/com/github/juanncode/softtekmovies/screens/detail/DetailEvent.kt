package com.github.juanncode.softtekmovies.screens.detail

sealed interface DetailEvent {
    data class GetMovie(val id: Long): DetailEvent

}