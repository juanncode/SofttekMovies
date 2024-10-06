@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.github.juanncode.softtekmovies.screens.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.juanncode.domain.Movie
import kotlin.math.roundToInt

@Composable
fun MovieItem(
    movie: Movie,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope?,
    onClickListener: (Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClickListener(movie.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.posterPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radius = 3.dp),
                    contentScale = ContentScale.Crop
                )



            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                with(sharedTransitionScope) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.posterPath)
                            .crossfade(true)
                            .build(),
                        contentDescription = movie.title,
                        modifier = Modifier
                            .height(200.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .sharedElement(
                                state = rememberSharedContentState(key = "image/${movie.id}"),
                                animatedVisibilityScope = animatedVisibilityScope!!
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RatingBar(
                        rating = movie.voteAverage,
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = String.format("%.1f", movie.voteAverage),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    stars: Int = 5
) {
    val filledStars = (rating * stars / 10).roundToInt()
    val unfilledStars = stars - filledStars

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.fillMaxHeight()
            )
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MaterialTheme {
        SharedTransitionScope {
            MovieItem(
                movie = Movie(
                    id = 1,
                    title = "Spider-Man: Across the Spider-Verse",
                    overview = "Miles Morales returns for the next chapter of the Spider-Verse saga.",
                    posterPath = "https://image.tmdb.org/t/p/w500/8Vt6mWEReuy4Of61Lnj5Xj704m8.jpg",
                    voteAverage = 8.5,
                    releaseDate = "2023-06-01",
                    page = 1
                ),
                onClickListener = {},
                sharedTransitionScope = this,
                animatedVisibilityScope = null
            )
        }

    }
}