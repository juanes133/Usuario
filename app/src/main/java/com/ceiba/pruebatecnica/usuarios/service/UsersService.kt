package com.ceiba.pruebatecnica.usuarios.service

import com.ceiba.pruebatecnica.usuarios.service.model.UsersServiceModel
import retrofit2.http.GET

interface UsersService {
    @GET("users")
    suspend fun listUsers(
    ): ArrayList<UsersServiceModel>
}