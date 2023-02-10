package com.ceiba.pruebatecnica.usuarios

import android.app.Application
import com.ceiba.pruebatecnica.usuarios.database.UsersDb
import com.ceiba.pruebatecnica.usuarios.repository.UserPostsRepository
import com.ceiba.pruebatecnica.usuarios.repository.UsersRepository

class UsersApplication : Application() {

    private val roomDatabase by lazy { UsersDb.getDatabase(this) }
    val usersRepository by lazy { UsersRepository(roomDatabase.usersDao()) }
    val postsUsersRepository by lazy { UserPostsRepository(roomDatabase.postsUsersDao()) }

}