package com.danieldisu.hnnotify.infrastructure.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpClientBuilder {

  fun build(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(
        HttpLoggingInterceptor()
          .setLevel(HttpLoggingInterceptor.Level.BODY)
      )
      .build()
  }

}
