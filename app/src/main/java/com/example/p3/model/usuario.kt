package com.example.p3.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "edad")
    val edad: Int,

    @ColumnInfo(name = "promedio")
    val promedio: Double,

    @ColumnInfo(name = "sigue_estudiando")
    val sigueEstudiando: Boolean
)
