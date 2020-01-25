package com.danieldisu.hnnotify.infrastructure.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
private const val contentType = "application/json"

class RetrofitHNServiceBuilder(
  okHttpClientBuilder: OkHttpClientBuilder
) {

  private val okHttpClient = okHttpClientBuilder.build()
  private val builder = getBuilder()

  fun <T> build(classOfT: Class<T>): T {
    return builder.create(classOfT)
  }

  private fun getBuilder(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(Json.asConverterFactory(contentType.toMediaType()))
      .build()
  }

}
