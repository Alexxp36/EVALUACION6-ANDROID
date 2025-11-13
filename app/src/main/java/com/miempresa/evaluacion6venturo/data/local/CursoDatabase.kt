package com.miempresa.evaluacion6venturo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos Room para la aplicación de Cursos TECSUP
 */
@Database(entities = [CursoEntity::class], version = 1, exportSchema = false)
abstract class CursoDatabase : RoomDatabase() {

    abstract fun cursoDao(): CursoDao

    companion object {
        @Volatile
        private var INSTANCE: CursoDatabase? = null

        /**
         * Obtiene la instancia singleton de la base de datos
         */
        fun getDatabase(context: Context): CursoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CursoDatabase::class.java,
                    "curso_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
