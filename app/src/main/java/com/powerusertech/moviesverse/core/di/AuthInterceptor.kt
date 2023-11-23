package com.powerusertech.moviesverse.core.di

import com.powerusertech.moviesverse.core.utils.Constants.APIKEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("accept", "application/json").addHeader("Authorization", "Bearer $APIKEY")
        return chain.proceed(request.build())

    }
}