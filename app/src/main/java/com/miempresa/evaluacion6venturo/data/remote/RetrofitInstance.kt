package com.miempresa.evaluacion6venturo.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Instancia singleton de Retrofit para hacer peticiones HTTP
 */
object RetrofitInstance {

    // URL base de la API configurada con MockAPI
    // La URL completa será: BASE_URL + "cursos" = https://6915526e84e8bd126af97ed7.mockapi.io/cursos
    private const val BASE_URL = "https://6915526e84e8bd126af97ed7.mockapi.io/"

    /**
     * Instancia de Retrofit configurada con Gson como convertidor JSON
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Instancia del servicio API de cursos
     */
    val api: CursoApiService by lazy {
        retrofit.create(CursoApiService::class.java)
    }
}
