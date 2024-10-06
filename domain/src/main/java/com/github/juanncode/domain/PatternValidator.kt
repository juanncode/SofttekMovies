package com.github.juanncode.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}