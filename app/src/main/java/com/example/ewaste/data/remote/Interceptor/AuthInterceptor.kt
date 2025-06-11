package com.example.ewaste.data.remote.interceptor

import com.example.ewaste.data.local.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
// import javax.inject.Inject  <-- HAPUS IMPORT INI

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            sessionManager.authToken.first()
        }
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", it)
            requestBuilder.addHeader("Accept", "application/json")
        }
        return chain.proceed(requestBuilder.build())
    }
}