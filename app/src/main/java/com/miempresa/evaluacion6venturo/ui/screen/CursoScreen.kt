package com.miempresa.evaluacion6venturo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miempresa.evaluacion6venturo.data.model.Curso
import com.miempresa.evaluacion6venturo.ui.viewmodel.CursoUiState

/**
 * Pantalla principal que muestra la lista de cursos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CursoScreen(
    uiState: CursoUiState,
    onSincronizarClick: () -> Unit,
    onDismissMessage: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cursos TECSUP",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onSincronizarClick,
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Text(
                    text = "Sincronizar",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Contenido principal
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Mostrar mensajes de error o éxito
                uiState.errorMessage?.let { mensaje ->
                    MensajeAlerta(
                        mensaje = mensaje,
                        esError = true,
                        onDismiss = onDismissMessage
                    )
                }

                uiState.successMessage?.let { mensaje ->
                    MensajeAlerta(
                        mensaje = mensaje,
                        esError = false,
                        onDismiss = onDismissMessage
                    )
                }

                // Lista de cursos
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.cursos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay cursos disponibles.\nPresiona 'Sincronizar' para cargar cursos.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                } else {
                    ListaCursos(cursos = uiState.cursos)
                }
            }
        }
    }
}

/**
 * Componente que muestra un mensaje de alerta
 */
@Composable
fun MensajeAlerta(
    mensaje: String,
    esError: Boolean,
    onDismiss: () -> Unit
) {
    val backgroundColor = if (esError) {
        Color(0xFFFFCDD2) // Rojo claro
    } else {
        Color(0xFFC8E6C9) // Verde claro
    }

    val textColor = if (esError) {
        Color(0xFFC62828) // Rojo oscuro
    } else {
        Color(0xFF2E7D32) // Verde oscuro
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = mensaje,
                color = textColor,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onDismiss) {
                Text("OK", color = textColor)
            }
        }
    }
}

/**
 * Componente que muestra la lista de cursos
 */
@Composable
fun ListaCursos(cursos: List<Curso>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cursos) { curso ->
            CursoItem(curso = curso)
        }
    }
}

/**
 * Componente que muestra un item de curso
 */
@Composable
fun CursoItem(curso: Curso) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            // Nombre del curso
            Text(
                text = curso.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Docente
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Docente: ",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = curso.docente,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Créditos y Ciclo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(
                        text = "Créditos: ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = curso.creditos.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Row {
                    Text(
                        text = "Ciclo: ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = curso.ciclo,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}
