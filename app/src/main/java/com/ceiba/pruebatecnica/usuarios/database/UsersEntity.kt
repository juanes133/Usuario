package com.ceiba.pruebatecnica.usuarios.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
class UsersEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,

)


