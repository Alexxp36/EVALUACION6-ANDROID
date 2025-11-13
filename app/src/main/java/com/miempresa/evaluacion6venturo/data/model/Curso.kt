package com.miempresa.evaluacion6venturo.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para un curso obtenido desde la API
 */
data class Curso(
    val id: Int,
    val nombre: String,
    val docente: String,
    @SerializedName("credito") // La API usa "credito" (singular)
    val creditos: Int,
    val ciclo: String
)
