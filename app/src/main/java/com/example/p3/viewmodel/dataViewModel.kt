

package com.example.p3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3.model.Usuario
import com.example.p3.repository.UsuariosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UsuariosViewModel(private val repository: UsuariosRepository) : ViewModel() {
    private val _usuariosList = MutableStateFlow<List<Usuario>>(emptyList())
    val usuariosList get() = _usuariosList

    init {
        viewModelScope.launch {
            repository.usuarios.collect { usuarios ->
                _usuariosList.value = usuarios
            }
        }
    }

    fun addUsuario(usuario: Usuario) = viewModelScope.launch {
        repository.insert(usuario)
    }

    fun updateUsuario(usuario: Usuario) = viewModelScope.launch {
        repository.update(usuario)
    }

    fun deleteUsuario(usuario: Usuario) = viewModelScope.launch {
        repository.delete(usuario)
    }
}