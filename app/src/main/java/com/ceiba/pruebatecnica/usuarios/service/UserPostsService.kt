package com.ceiba.pruebatecnica.usuarios.service

import com.ceiba.pruebatecnica.usuarios.model.UserPostsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UserPostsService {
    @GET("posts")
    suspend fun postsListUser(
        @Query("userId") id: Int,
    ): ArrayList<UserPostsModel>
}