package com.miempresa.evaluacion6venturo.data.remote

import com.miempresa.evaluacion6venturo.data.model.Curso
import retrofit2.http.GET

/**
 * Interfaz de servicio API para obtener cursos desde el servidor
 */
interface CursoApiService {

    /**
     * Obtiene la lista de cursos desde el endpoint
     * @return Lista de cursos disponibles
     */
    @GET("cursos")
    suspend fun getCursos(): List<Curso>
}
