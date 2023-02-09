package com.ceiba.pruebatecnica.usuarios.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM users_table")
    suspend fun getAll(): List<UsersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UsersEntity>)

    @Query("DELETE FROM users_table WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM users_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM users_table WHERE id = :id")
    suspend fun getById(id: String): List<UsersEntity>

}
