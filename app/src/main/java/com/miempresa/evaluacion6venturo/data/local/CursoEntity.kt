package com.miempresa.evaluacion6venturo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miempresa.evaluacion6venturo.data.model.Curso

/**
 * Entidad Room para persistir cursos localmente
 */
@Entity(tableName = "cursos")
data class CursoEntity(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val docente: String,
    val creditos: Int,
    val ciclo: String
)

/**
 * Convierte un Curso del API a CursoEntity para Room
 */
fun Curso.toEntity(): CursoEntity {
    return CursoEntity(
        id = id,
        nombre = nombre,
        docente = docente,
        creditos = creditos,
        ciclo = ciclo
    )
}

/**
 * Convierte un CursoEntity de Room a Curso
 */
fun CursoEntity.toDomain(): Curso {
    return Curso(
        id = id,
        nombre = nombre,
        docente = docente,
        creditos = creditos,
        ciclo = ciclo
    )
}
