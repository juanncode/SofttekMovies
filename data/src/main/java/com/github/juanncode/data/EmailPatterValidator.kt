package com.github.juanncode.data

import android.util.Patterns
import com.github.juanncode.domain.PatternValidator
import javax.inject.Inject

class EmailPatterValidator @Inject constructor() : PatternValidator{
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}