package com.github.juanncode.data.retrofit

import com.github.juanncode.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        val requestBuilder = originRequest.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN_MOVIES}")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}