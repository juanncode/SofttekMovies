package com.github.juanncode.softtekmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.juanncode.softtekmovies.config.AppRouter
import com.github.juanncode.softtekmovies.screens.home.HomeScreen
import com.github.juanncode.softtekmovies.screens.home.HomeViewModel
import com.github.juanncode.softtekmovies.ui.theme.SofttekMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SofttekMoviesTheme {
                val navController = rememberNavController()

                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = AppRouter.HomeRoute,

                        ) {
                        composable<AppRouter.HomeRoute> {
                            val viewModel = hiltViewModel<HomeViewModel>()
                            HomeScreen(
                                state = viewModel.state,
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
