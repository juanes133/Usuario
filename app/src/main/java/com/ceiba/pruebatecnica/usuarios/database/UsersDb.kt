package com.ceiba.pruebatecnica.usuarios.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ceiba.pruebatecnica.usuarios.database.daos.UserPostsDao
import com.ceiba.pruebatecnica.usuarios.database.daos.UsersDao
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersPostsEntity
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersEntity

@Database(entities = [UsersEntity::class, UsersPostsEntity::class], version = 1)
abstract class UsersDb : RoomDatabase() {

    abstract fun usersDao(): UsersDao
    abstract fun postsUsersDao(): UserPostsDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDb? = null

        fun getDatabase(context: Context): UsersDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDb::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
