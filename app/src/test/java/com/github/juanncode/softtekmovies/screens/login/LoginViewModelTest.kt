@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.juanncode.softtekmovies.screens.login

import androidx.compose.foundation.text.input.TextFieldState
import com.github.juanncode.domain.UserDataValidator
import com.github.juanncode.domain.utils.ErrorGeneric
import com.github.juanncode.softtekmovies.screens.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    @Mock private lateinit var userDataValidator: UserDataValidator

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = LoginViewModel(userDataValidator)
    }

    @Test
    fun `Email validation is triggered when the email entry is modified`() = runTest{
        val validEmail = "example@test.es"
        whenever(userDataValidator.isValidEmail(validEmail)).thenReturn(true)

        viewModel.validateEmail()
        viewModel.state = viewModel.state.copy(email = TextFieldState(validEmail))

        assertTrue(viewModel.state.isEmailValid)
    }

    @Test
    fun `Password validation is triggered when password is modified`() = runTest {
        val validPassword = "password123"
        whenever(userDataValidator.isValidPassword(validPassword)).thenReturn(true)
        viewModel.validatePassword()

        viewModel.state = viewModel.state.copy(password = TextFieldState(validPassword))
        advanceUntilIdle()

        assertTrue(viewModel.state.isPasswordValid)
    }

    @Test
    fun `Toggle password visibility updates state correctly`() {
        assertFalse(viewModel.state.isPasswordVisible)

        viewModel.onEvent(LoginEvent.OnTogglePasswordVisibilityClick)

        assertTrue(viewModel.state.isPasswordVisible)

        viewModel.onEvent(LoginEvent.OnTogglePasswordVisibilityClick)

        assertFalse(viewModel.state.isPasswordVisible)
    }

    @Test
    fun `Login succeeds with correct credentials`() = runTest {
        viewModel.state = viewModel.state.copy(
            email = TextFieldState("example@test.es"),
            password = TextFieldState("123")
        )

        viewModel.onEvent(LoginEvent.OnLoginClick)

        advanceUntilIdle()

        assertTrue(viewModel.state.success)
        assertNull(viewModel.state.error)
    }

    @Test
    fun `Login fails with incorrect credentials`() = runTest {
        viewModel.state = viewModel.state.copy(
            email = TextFieldState("wrong@example.com"),
            password = TextFieldState("wrong_password")
        )

        viewModel.onEvent(LoginEvent.OnLoginClick)

        advanceUntilIdle()

        assertFalse(viewModel.state.success)
        assertNotNull(viewModel.state.error)
        assertEquals("Correo o contraseña incorrectos", viewModel.state.error?.userMessage)
    }

    @Test
    fun `Clean error event resets the error state`() {
        viewModel.state = viewModel.state.copy(
            error = ErrorGeneric(0, "error", "Mensaje de error")
        )

        viewModel.onEvent(LoginEvent.CleanError)

        assertNull(viewModel.state.error)
    }

}