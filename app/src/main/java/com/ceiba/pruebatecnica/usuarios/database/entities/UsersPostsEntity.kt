package com.ceiba.pruebatecnica.usuarios.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_users_table")
class UsersPostsEntity(
    @PrimaryKey
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)