@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.juanncode.softtekmovies.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.github.juanncode.softtekmovies.screens.components.GradientBackground
import com.github.juanncode.softtekmovies.screens.detail.components.MovieDetailItem
import com.github.juanncode.softtekmovies.screens.components.SofttekToolbar

@Composable
fun DetailScreen(
    idMovie: Long,
    onEvent: (DetailEvent) -> Unit,
    state: DetailState,
    navController: NavController
) {

    LaunchedEffect(key1 = true) {
        onEvent(DetailEvent.GetMovie(idMovie))
    }
    GradientBackground {
        Column {
            if (state.isLoading)
                CircularProgressIndicator()
            state.movie?.let {
                SofttekToolbar(
                    title = it.title,
                    showBackButton = true,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
                MovieDetailItem(movie = state.movie)
            }
        }
    }


}