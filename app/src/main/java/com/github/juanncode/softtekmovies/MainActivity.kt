@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.github.juanncode.softtekmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.juanncode.softtekmovies.config.AppRouter
import com.github.juanncode.softtekmovies.screens.detail.DetailScreen
import com.github.juanncode.softtekmovies.screens.detail.DetailViewModel
import com.github.juanncode.softtekmovies.screens.home.HomeScreen
import com.github.juanncode.softtekmovies.screens.home.HomeViewModel
import com.github.juanncode.softtekmovies.screens.login.LoginScreen
import com.github.juanncode.softtekmovies.screens.login.LoginViewModel
import com.github.juanncode.softtekmovies.ui.theme.SofttekMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SofttekMoviesTheme {

                Surface {

                    SharedTransitionLayout {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = AppRouter.LoginRoute,

                            ) {
                            composable<AppRouter.HomeRoute> {
                                val viewModel = hiltViewModel<HomeViewModel>()
                                val state = viewModel.state.collectAsState().value
                                HomeScreen(
                                    state = state,
                                    onEvent = {
                                        viewModel.onEvent(it)
                                    },
                                    navController,
                                    animatedVisibilityScope = this
                                )
                            }
                            composable<AppRouter.DetailRoute> {
                                val args = it.toRoute<AppRouter.DetailRoute>()
                                val viewModel = hiltViewModel<DetailViewModel>()
                                DetailScreen(
                                    idMovie = args.idMovie,
                                    state = viewModel.state,
                                    navController = navController,
                                    onEvent = {
                                        viewModel.onEvent(it)
                                    },
                                    animatedVisibilityScope = this
                                )
                            }
                            composable<AppRouter.LoginRoute> {
                                val viewModel = hiltViewModel<LoginViewModel>()
                                LoginScreen(
                                    state = viewModel.state,
                                    navController = navController,
                                    onEvent = {
                                        viewModel.onEvent(it)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
