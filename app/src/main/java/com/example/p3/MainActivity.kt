package com.example.p3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.p3.repository.UsuariosRepository
import com.example.p3.room.UsuariosDataBase
import com.example.p3.ui.theme.theme.P3Theme
import com.example.p3.ui.theme.views.UsuariosView
import com.example.p3.viewmodel.UsuariosViewModel


class MainActivity : ComponentActivity() {
    private lateinit var usuariosViewModel: UsuariosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar la base de datos y el repositorio
        val database = Room.databaseBuilder(
            applicationContext,
            UsuariosDataBase::class.java,
            "usuarios_database"
        ).build()

        val repository = UsuariosRepository(database.usuariosDao())

        // Inicializar el ViewModel
        usuariosViewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(UsuariosViewModel::class.java)

        setContent {
            P3Theme {

                    // Llama a la vista principal con el formulario y los botones
                    UsuariosView(usuariosViewModel = usuariosViewModel)

            }
        }
    }
}

// ViewModelFactory para crear el ViewModel con el repositorio
class ViewModelFactory(private val repository: UsuariosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuariosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuariosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
