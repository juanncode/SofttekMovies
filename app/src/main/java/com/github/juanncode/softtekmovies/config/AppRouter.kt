package com.github.juanncode.softtekmovies.config

import kotlinx.serialization.Serializable

sealed class AppRouter {

    @Serializable
    data object HomeRoute: AppRouter()
    @Serializable
    data class DetailRoute(val id: Int) : AppRouter()
}