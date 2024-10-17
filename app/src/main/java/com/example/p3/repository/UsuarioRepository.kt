


package com.example.p3.repository

import com.example.p3.model.Usuario
import com.example.p3.room.UsuariosDao
import kotlinx.coroutines.flow.Flow
class UsuariosRepository(private val usuariosDao: UsuariosDao) {
    val usuarios: Flow<List<Usuario>> = usuariosDao.getUsuarios()

    suspend fun insert(usuario: Usuario) {
        usuariosDao.insert(usuario)
    }

    suspend fun update(usuario: Usuario) {
        usuariosDao.update(usuario)
    }

    suspend fun delete(usuario: Usuario) {
        usuariosDao.delete(usuario)
    }
}
