package com.miempresa.evaluacion6venturo.data.repository

import com.miempresa.evaluacion6venturo.data.local.CursoDao
import com.miempresa.evaluacion6venturo.data.local.toDomain
import com.miempresa.evaluacion6venturo.data.local.toEntity
import com.miempresa.evaluacion6venturo.data.model.Curso
import com.miempresa.evaluacion6venturo.data.remote.CursoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio que maneja las operaciones de datos de cursos
 * Coordina entre la fuente de datos local (Room) y remota (Retrofit)
 */
class CursoRepository(
    private val cursoDao: CursoDao,
    private val apiService: CursoApiService
) {

    /**
     * Obtiene todos los cursos desde la base de datos local
     */
    suspend fun getCursosFromLocal(): List<Curso> = withContext(Dispatchers.IO) {
        cursoDao.getAll().map { it.toDomain() }
    }

    /**
     * Sincroniza cursos desde la API remota
     * 1. Descarga los cursos desde la API
     * 2. Borra los cursos locales
     * 3. Guarda los nuevos cursos en la base de datos local
     * @return Lista de cursos actualizados
     */
    suspend fun sincronizarCursos(): List<Curso> = withContext(Dispatchers.IO) {
        try {
            // Descargar cursos desde la API
            val cursosRemote = apiService.getCursos()

            // Borrar datos locales
            cursoDao.deleteAll()

            // Guardar nuevos cursos en Room
            val entities = cursosRemote.map { it.toEntity() }
            cursoDao.insertAll(entities)

            // Retornar los cursos actualizados
            cursosRemote
        } catch (e: Exception) {
            // En caso de error, retornar los cursos locales si existen
            throw e
        }
    }

    /**
     * Verifica si hay cursos en la base de datos local
     */
    suspend fun hayCursosLocales(): Boolean = withContext(Dispatchers.IO) {
        cursoDao.getAll().isNotEmpty()
    }
}
