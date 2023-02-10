package com.ceiba.pruebatecnica.usuarios.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersPostsEntity

@Dao
interface UserPostsDao {

    @Query("SELECT * FROM posts_users_table")
    suspend fun getAll(): List<UsersPostsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UsersPostsEntity>)

    @Query("DELETE FROM posts_users_table WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM posts_users_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM posts_users_table WHERE userId = :id")
    suspend fun getById(id: Int): List<UsersPostsEntity>
}