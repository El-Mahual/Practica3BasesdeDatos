package com.example.p3.ui.theme.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.p3.model.Usuario
import com.example.p3.viewmodel.UsuariosViewModel
@Composable
fun UsuariosView(usuariosViewModel: UsuariosViewModel) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var promedio by remember { mutableStateOf("") }
    var sigueEstudiando by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    // Variables para controlar qué mostrar
    var showNombres by remember { mutableStateOf(false) }
    var showEdades by remember { mutableStateOf(false) }
    var showPromedios by remember { mutableStateOf(false) }
    var showSigueEstudiando by remember { mutableStateOf(false) }

    // Recolecta la lista de usuarios desde el ViewModel
    val usuarios by usuariosViewModel.usuariosList.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Sección del formulario
        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        TextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Teclado numérico
        )
        TextField(
            value = promedio,
            onValueChange = { promedio = it },
            label = { Text(text = "Promedio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Teclado numérico
        )
        Row {
            Text("¿Sigue estudiando?")
            Switch(checked = sigueEstudiando, onCheckedChange = { sigueEstudiando = it })
        }

        Button(onClick = {
            // Verificar que todos los campos estén llenos
            if (nombre.isEmpty() || edad.isEmpty() || promedio.isEmpty()) {
                errorMessage = "Por favor, llena todos los campos."
                successMessage = "" // Limpiar mensaje de éxito
            } else {
                errorMessage = "" // Limpiar mensaje de error
                // Crear un nuevo usuario y guardar en la base de datos
                val nuevoUsuario = Usuario(
                    nombre = nombre,
                    edad = edad.toInt(),
                    promedio = promedio.toFloat().toDouble(),
                    sigueEstudiando = sigueEstudiando
                )
                usuariosViewModel.addUsuario(nuevoUsuario)
                // Limpiar el formulario después de agregar
                nombre = ""
                edad = ""
                promedio = ""
                sigueEstudiando = false

                // Mensaje de éxito
                successMessage = "Usuario agregado con éxito."
            }
        }) {
            Text("Agregar Usuario")
        }

        // Mostrar mensaje de error si existe
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Mostrar mensaje de éxito si existe
        if (successMessage.isNotEmpty()) {
            Text(
                text = successMessage,
                color = Color.Green,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
//
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre formulario y botones

        // Botones para mostrar información
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { showNombres = !showNombres }) { Text(if (showNombres) "Ocultar Nombres" else "Mostrar Nombres") }
            Button(onClick = { showEdades = !showEdades }) { Text(if (showEdades) "Ocultar Edades" else "Mostrar Edades") }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { showPromedios = !showPromedios }) { Text(if (showPromedios) "Ocultar Promedios" else "Mostrar Promedios") }
            Button(onClick = { showSigueEstudiando = !showSigueEstudiando }) { Text(if (showSigueEstudiando) "Ocultar Sigue Estudiando" else "Mostrar Sigue Estudiando") }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre botones y la lista

        // Mostrar la información en formato de tabla
        LazyColumn {
            items(usuarios) { usuario ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("ID: ${usuario.id}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        if (showNombres) Text("Nombre: ${usuario.nombre}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        if (showEdades) Text("Edad: ${usuario.edad}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        if (showPromedios) Text("Promedio: ${usuario.promedio}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        if (showSigueEstudiando) Text("Sigue Estudiando: ${if (usuario.sigueEstudiando) "Sí" else "No"}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre lista y botón borrar

        Button(
            onClick = {
                if (usuarios.isNotEmpty()) {
                    usuariosViewModel.deleteUsuario(usuarios.last())
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Cambia 'backgroundColor' a 'containerColor'
        ) {
            Text("Borrar Último Usuario", color = Color.White) // Cambia el texto a blanco para que sea visible
        }

    }
}
