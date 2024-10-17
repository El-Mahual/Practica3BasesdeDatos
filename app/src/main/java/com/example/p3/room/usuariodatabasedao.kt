package com.example.p3.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import com.example.p3.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuariosDao {
    @Query("SELECT * FROM usuarios")
    fun getUsuarios(): Flow<List<Usuario>>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun getUsuarioById(id: Long): Flow<Usuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario)

    @Update
    suspend fun update(usuario: Usuario)

    @Delete
    suspend fun delete(usuario: Usuario)
}

