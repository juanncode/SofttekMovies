@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.juanncode.data.util

import com.github.juanncode.domain.utils.ErrorGeneric
import com.github.juanncode.domain.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class SafeApiCallKtTest {




    @Test
    fun `test safeApiCall with success`() = runTest {
        launch {
            val expectResult = "Success"
            val apiCall: suspend () -> String = {
                expectResult
            }
            val result = safeApiCall(Dispatchers.IO, apiCall)

            assertEquals(Resource.Success(expectResult), result)
        }

    }

    @Test
    fun `test safeApiCall returns Error when IOException occurs`() = runTest {
        val apiCall: suspend () -> String = { throw IOException("Network error") }

        val result = safeApiCall(Dispatchers.IO, apiCall)

        assertEquals(
            Resource.Error(
                ErrorGeneric(
                    code = 0,
                    userMessage = "Tuvimos un problema de conectividad, por favor revise su conexión a internet y vuelva a intentarlo",
                    error = (result as Resource.Error).error?.error
                )
            ),
            result
        )
    }

    @Test
    fun `test safeApiCall returns Error when HttpException occurs`() = runTest {
        val httpException = mockk<HttpException>(relaxed = true)
        coEvery { httpException.code() } returns 404
        coEvery { httpException.message } returns "Not Found"

        val apiCall: suspend () -> String = { throw httpException }

        val result = safeApiCall(Dispatchers.IO, apiCall)

        assertEquals(Resource.Error(ErrorGeneric(404, "Not Found", (result as Resource.Error).error?.error)), result)
    }

    @Test
    fun `test safeApiCall returns Error for generic Throwable`() = runTest {
        val throwable = Throwable("Generic error")
        val apiCall: suspend () -> String = { throw throwable }

        val result = safeApiCall(Dispatchers.IO, apiCall)

        assertEquals(Resource.Error(ErrorGeneric(0, "Generic error", (result as Resource.Error).error?.error)), result)
    }
}