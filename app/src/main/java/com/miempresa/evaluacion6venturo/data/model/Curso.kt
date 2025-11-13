package com.miempresa.evaluacion6venturo.data.model

/**
 * Modelo de datos para un curso obtenido desde la API
 */
data class Curso(
    val id: Int,
    val nombre: String,
    val docente: String,
    val creditos: Int,
    val ciclo: String
)
