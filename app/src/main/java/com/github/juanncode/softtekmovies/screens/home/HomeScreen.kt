@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.juanncode.softtekmovies.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.juanncode.domain.Movie
import com.github.juanncode.softtekmovies.ui.theme.SofttekMoviesTheme


@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,

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

    PullToRefreshBox(
        isRefreshing = state.loading,
        onRefresh = {
            onEvent(HomeEvent.RefreshMovies)
        }
    ) {
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(count = 3),
            contentPadding = PaddingValues(horizontal = 5.dp, vertical = 10.dp)
        ) {
            items(
                state.movies
            ) {
                MovieItem(movie = it)
            }

            if (state.loading) {
                item(span = { GridItemSpan(3) }) {
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


@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()

            .height(300.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(120.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                ,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = movie.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Rating: ${movie.voteAverage}",
                    style = MaterialTheme.typography.bodySmall
                )
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
            onEvent = {}
        )
    }

}