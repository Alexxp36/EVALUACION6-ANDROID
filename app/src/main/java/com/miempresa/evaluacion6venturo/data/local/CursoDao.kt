package com.miempresa.evaluacion6venturo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object para operaciones de base de datos de cursos
 */
@Dao
interface CursoDao {

    /**
     * Inserta una lista de cursos en la base de datos
     * Si hay conflicto (mismo ID), reemplaza el curso existente
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cursos: List<CursoEntity>)

    /**
     * Obtiene todos los cursos de la base de datos
     */
    @Query("SELECT * FROM cursos")
    suspend fun getAll(): List<CursoEntity>

    /**
     * Elimina todos los cursos de la base de datos
     */
    @Query("DELETE FROM cursos")
    suspend fun deleteAll()
}
