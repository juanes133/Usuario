package com.ceiba.pruebatecnica.usuarios.repository

import com.ceiba.pruebatecnica.usuarios.service.UsersService
import com.ceiba.pruebatecnica.usuarios.database.UsersDao
import com.ceiba.pruebatecnica.usuarios.database.UsersEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersRepository(private val usersDao: UsersDao) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: UsersService = retrofit.create(UsersService::class.java)

    suspend fun getUsers(
        onSuccess: (ArrayList<UsersEntity>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            val result = ArrayList<UsersEntity>()
            service.listUsers().forEach {
                result.add(
                    UsersEntity(
                        it.id,
                        it.name,
                        it.phone,
                        it.email)
                )
            }
            onSuccess(result)
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
