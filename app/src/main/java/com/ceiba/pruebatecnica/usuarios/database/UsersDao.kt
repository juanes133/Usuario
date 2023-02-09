package com.ceiba.pruebatecnica.usuarios.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {

    @Query("SELECT * FROM users_table")
    suspend fun getAll(): List<UsersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: UsersEntity)

    @Query("DELETE FROM users_table WHERE id = :id")
    suspend fun deleteAll(id: String)

    @Query("SELECT * FROM users_table WHERE id = :id")
    suspend fun getById(id: String): List<UsersEntity>

}
