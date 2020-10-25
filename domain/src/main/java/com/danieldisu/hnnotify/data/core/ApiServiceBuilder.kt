package com.danieldisu.hnnotify.data.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlin.reflect.KClass

object ApiServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    fun <Service : Any> build(serviceClass: KClass<Service>): Service =
        retrofit.create(serviceClass.java)
}