package com.github.juanncode.domain

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class UserDataValidatorTest {

    @Mock lateinit var patternValidator: PatternValidator
    @Mock lateinit var userDataValidator: UserDataValidator

    @Before
    fun setUp() {
        userDataValidator = UserDataValidator(patternValidator)
    }

    @Test
    fun `isValidEmail should return true for valid email`() {
        val validEmail = "test@example.com"
        `when`(patternValidator.matches(validEmail)).thenReturn(true)

        val result = userDataValidator.isValidEmail(validEmail)

        assertTrue(result)
        verify(patternValidator).matches(validEmail.trim())
    }

    @Test
    fun `isValidEmail should return false for invalid email`() {
        val invalidEmail = "invalid-email"
        `when`(patternValidator.matches(invalidEmail)).thenReturn(false)

        val result = userDataValidator.isValidEmail(invalidEmail)

        assertFalse(result)
        verify(patternValidator).matches(invalidEmail.trim())
    }

    @Test
    fun `isValidPassword should return true when password length is greater than or equal to MIN_PASSWORD_LENGTH`() {
        val validPassword = "password"

        val result = userDataValidator.isValidPassword(validPassword)

        assertTrue(result)
    }

    @Test
    fun `isValidPassword should return false when password length is less than MIN_PASSWORD_LENGTH`() {
        val shortPassword = ""

        val result = userDataValidator.isValidPassword(shortPassword)

        assertFalse(result)
    }
}