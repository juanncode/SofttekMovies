package com.github.juanncode.domain.utils

data class ErrorGeneric(val code: Int, val userMessage: String?, val error: String?): Exception()
