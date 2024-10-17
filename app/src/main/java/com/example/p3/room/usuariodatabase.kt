package com.example.p3.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.p3.model.Usuario

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class UsuariosDataBase: RoomDatabase() {
    abstract fun usuariosDao(): UsuariosDao

}
