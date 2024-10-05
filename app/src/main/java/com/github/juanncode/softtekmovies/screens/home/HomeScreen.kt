@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.juanncode.softtekmovies.screens.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.juanncode.domain.Movie
import com.github.juanncode.softtekmovies.R
import com.github.juanncode.softtekmovies.config.AppRouter
import com.github.juanncode.softtekmovies.screens.components.GradientBackground
import com.github.juanncode.softtekmovies.screens.home.components.MovieItem
import com.github.juanncode.softtekmovies.screens.home.components.SofttekToolbar
import com.github.juanncode.softtekmovies.ui.theme.SofttekMoviesTheme

private val columns = 2

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val listState = rememberLazyGridState()

    val reachedBottom by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(
                context,
                state.error.message,
                Toast.LENGTH_LONG
            ).show()
            onEvent(HomeEvent.CleanError)

        }
    }

    LaunchedEffect(key1 = reachedBottom) {
        if (reachedBottom && state.loading.not()) {
            onEvent(HomeEvent.GetNewMovies)
        }
    }

    GradientBackground {
        PullToRefreshBox(
            isRefreshing = state.loading,
            onRefresh = {
                onEvent(HomeEvent.RefreshMovies)
            }
        ) {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(count = columns),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 10.dp)
            ) {
                item(span = { GridItemSpan(columns) }) {
                    SofttekToolbar(
                        title = stringResource(id = R.string.movies_blue)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.movie_logo),
                            contentDescription = "bet"
                        )
                    }
                }
                items(
                    state.movies
                ) { movie ->
                    MovieItem(movie = movie) {
                        navController.navigate(AppRouter.DetailRoute(idMovie = movie.id))
                    }
                }

                if (state.loading) {
                    item(span = { GridItemSpan(columns) }) {
                        Column(
                            modifier = Modifier.padding(vertical = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Cargando...",
                                style = TextStyle(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }
                }

            }


        }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {

    SofttekMoviesTheme {
        HomeScreen(
            state = HomeState(
                movies = listOf(
                    Movie(
                        id = 1,
                        title = "Movie 1",
                        overview = "Overview 1",
                        posterPath = "https://example.com/poster1.jpg",
                        voteAverage = 7.5,
                        releaseDate = "2022-01-01",
                        page = 2
                    ),
                    Movie(
                        id = 1,
                        title = "Movie 1",
                        overview = "Overview 1",
                        posterPath = "https://example.com/poster1.jpg",
                        voteAverage = 7.5,
                        releaseDate = "2022-01-01",
                        page = 2
                    ),
                    Movie(
                        id = 1,
                        title = "Movie 1",
                        overview = "Overview 1",
                        posterPath = "https://example.com/poster1.jpg",
                        voteAverage = 7.5,
                        releaseDate = "2022-01-01",
                        page = 2
                    )
                )
            ),
            onEvent = {},
            navController = rememberNavController()
        )
    }

}