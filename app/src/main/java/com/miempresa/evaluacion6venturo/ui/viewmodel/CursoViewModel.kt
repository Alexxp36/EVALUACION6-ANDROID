package com.miempresa.evaluacion6venturo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miempresa.evaluacion6venturo.data.model.Curso
import com.miempresa.evaluacion6venturo.data.repository.CursoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado de la UI para la pantalla de cursos
 */
data class CursoUiState(
    val cursos: List<Curso> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

/**
 * ViewModel para la pantalla de cursos
 * Maneja la lógica de negocio y el estado de la UI
 */
class CursoViewModel(
    private val repository: CursoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CursoUiState())
    val uiState: StateFlow<CursoUiState> = _uiState.asStateFlow()

    init {
        cargarCursos()
    }

    /**
     * Carga los cursos desde la base de datos local
     * Si no hay cursos locales, intenta sincronizar desde la API
     */
    fun cargarCursos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                // Primero intentar cargar desde local
                val cursosLocales = repository.getCursosFromLocal()

                if (cursosLocales.isEmpty()) {
                    // Si no hay cursos locales, sincronizar desde la API
                    sincronizarCursos()
                } else {
                    // Mostrar cursos locales
                    _uiState.value = _uiState.value.copy(
                        cursos = cursosLocales,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar cursos: ${e.message}"
                )
            }
        }
    }

    /**
     * Sincroniza los cursos desde la API
     * Borra los datos locales y descarga los nuevos cursos
     */
    fun sincronizarCursos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )

            try {
                val cursosActualizados = repository.sincronizarCursos()
                _uiState.value = _uiState.value.copy(
                    cursos = cursosActualizados,
                    isLoading = false,
                    successMessage = "Cursos sincronizados correctamente"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al sincronizar: ${e.message}"
                )
            }
        }
    }

    /**
     * Limpia los mensajes de error y éxito
     */
    fun limpiarMensajes() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}
