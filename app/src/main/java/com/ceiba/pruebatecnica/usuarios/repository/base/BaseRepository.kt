package com.ceiba.pruebatecnica.usuarios.repository.base

import com.ceiba.pruebatecnica.usuarios.service.PostsUsersService
import com.ceiba.pruebatecnica.usuarios.service.UsersService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: UsersService = retrofit.create(UsersService::class.java)
    val servicePosts: PostsUsersService = retrofit.create(PostsUsersService::class.java)
}