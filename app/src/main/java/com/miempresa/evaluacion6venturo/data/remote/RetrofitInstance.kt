package com.miempresa.evaluacion6venturo.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Instancia singleton de Retrofit para hacer peticiones HTTP
 */
object RetrofitInstance {

    // URL base de la API
    // Nota: Esta URL usa MockAPI.io como servicio de API simulada
    // Para producción, reemplaza con la URL real de la API de TECSUP
    private const val BASE_URL = "https://67344eba5995834c8a933a36.mockapi.io/api/v1/"

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
