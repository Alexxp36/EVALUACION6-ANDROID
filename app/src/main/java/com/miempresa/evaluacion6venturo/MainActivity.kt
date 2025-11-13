package com.miempresa.evaluacion6venturo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miempresa.evaluacion6venturo.data.local.CursoDatabase
import com.miempresa.evaluacion6venturo.data.remote.RetrofitInstance
import com.miempresa.evaluacion6venturo.data.repository.CursoRepository
import com.miempresa.evaluacion6venturo.ui.screen.CursoScreen
import com.miempresa.evaluacion6venturo.ui.theme.Evaluacion6VenturoTheme
import com.miempresa.evaluacion6venturo.ui.viewmodel.CursoViewModel

/**
 * Actividad principal de la aplicación Cursos TECSUP
 */
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: CursoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar Room Database
        val database = CursoDatabase.getDatabase(applicationContext)
        val cursoDao = database.cursoDao()

        // Inicializar Retrofit API Service
        val apiService = RetrofitInstance.api

        // Inicializar Repository
        val repository = CursoRepository(cursoDao, apiService)

        // Inicializar ViewModel usando Factory
        viewModel = ViewModelProvider(
            this,
            CursoViewModelFactory(repository)
        )[CursoViewModel::class.java]

        setContent {
            Evaluacion6VenturoTheme {
                val uiState by viewModel.uiState.collectAsState()

                CursoScreen(
                    uiState = uiState,
                    onSincronizarClick = { viewModel.sincronizarCursos() },
                    onDismissMessage = { viewModel.limpiarMensajes() }
                )
            }
        }
    }
}

/**
 * Factory para crear instancias de CursoViewModel con dependencias
 */
class CursoViewModelFactory(
    private val repository: CursoRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CursoViewModel::class.java)) {
            return CursoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}