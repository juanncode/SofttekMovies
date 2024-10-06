package com.github.juanncode.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email: String) : Boolean {
        return patternValidator.matches(email.trim())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }
    companion object {
        const val MIN_PASSWORD_LENGTH = 1
    }
}