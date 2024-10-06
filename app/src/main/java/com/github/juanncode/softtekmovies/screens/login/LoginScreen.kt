package com.github.juanncode.softtekmovies.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.juanncode.softtekmovies.R
import com.github.juanncode.softtekmovies.config.AppRouter
import com.github.juanncode.softtekmovies.screens.components.MovieActionButton
import com.github.juanncode.softtekmovies.screens.components.MovieGenericTextField
import com.github.juanncode.softtekmovies.screens.components.MoviePasswordTextField
import com.github.juanncode.softtekmovies.screens.components.GradientBackground
import com.github.juanncode.softtekmovies.ui.theme.EmailIcon
import com.github.juanncode.softtekmovies.ui.theme.SofttekMoviesTheme


@Composable

fun LoginScreen(

    state: LoginState,

    onEvent: (LoginEvent) -> Unit,
    navController: NavController

) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(
                context,
                state.error.userMessage,
                Toast.LENGTH_LONG
            ).show()
            onEvent(LoginEvent.CleanError)

        }
    }

    LaunchedEffect(key1 = state.success) {
        if (state.success) {
            println("emtrp por aqio")
            navController.navigate(AppRouter.HomeRoute){
                popUpTo(AppRouter.LoginRoute) {
                    inclusive = true
                }
            }
        }
    }

    GradientBackground {

        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)

        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.movie_logo),
                    contentDescription = "bet",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Bet Blue",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.welcome_back),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))
            MovieGenericTextField(
                state = state.email,
                endIcon = null,
                startIcon = EmailIcon,
                hint = "example@test.es",
                title = "Email",
                error = if (state.isEmailValid.not()) stringResource(id = R.string.must_be_a_valid_email) else null,
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(15.dp))
            MoviePasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onEvent(LoginEvent.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password)
            )
            Spacer(modifier = Modifier.height(20.dp))
            MovieActionButton(
                text = stringResource(id = R.string.login),
                onClick = {
                    onEvent(LoginEvent.OnLoginClick)
                },
                isLoading = state.isLoggingIn,
                enabled = state.canLogin
            )
        }

    }


}

@Preview

@Composable

private fun LoginScreenPreview() {

    SofttekMoviesTheme {

        LoginScreen(

            state = LoginState(),

            onEvent = {},
            navController = rememberNavController()

        )

    }

}