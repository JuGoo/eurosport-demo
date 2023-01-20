package com.eurosport.data.api

import com.eurosport.data.services.EurosportService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ApiClient {
    fun getEurosportService(): EurosportService
}

internal class ApiClientImpl(
    private val retrofit: Retrofit = createRetrofit()
) : ApiClient {
    override fun getEurosportService(): EurosportService = retrofit.create(EurosportService::class.java)
}

private fun createRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(createHttpClient())
    .addConverterFactory(createConverterFactory())
    .build()

private fun createHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    .addInterceptor(createLoggingInterceptor())
    .build()

private fun createLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

private fun createConverterFactory(): Converter.Factory = GsonConverterFactory.create()

private const val READ_TIMEOUT_IN_SECONDS: Long = 60
private const val BASE_URL: String = "https://extendsclass.com"