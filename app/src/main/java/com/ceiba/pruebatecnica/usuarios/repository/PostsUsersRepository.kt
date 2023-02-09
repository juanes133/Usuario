package com.ceiba.pruebatecnica.usuarios.repository

import com.ceiba.pruebatecnica.usuarios.database.daos.PostsUsersDao
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersPostsEntity
import com.ceiba.pruebatecnica.usuarios.repository.base.BaseRepository

class PostsUsersRepository(private val postsUsersDao: PostsUsersDao) : BaseRepository() {

    suspend fun getUserPosts(
        id: Int,
        onSuccess: (ArrayList<UsersPostsEntity>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            val result = ArrayList<UsersPostsEntity>()
            servicePosts.postsListUser(id).forEach {
                result.add(
                    UsersPostsEntity(
                        it.userId,
                        it.id,
                        it.title,
                        it.body)
                )
            }
            onSuccess(result)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    suspend fun getUserPostsCache(id: Int): ArrayList<UsersPostsEntity> {
        return postsUsersDao.getById(id) as ArrayList<UsersPostsEntity>
    }

    suspend fun insertUserPostsCache(list: ArrayList<UsersPostsEntity>) {
        postsUsersDao.insertAll(list)
    }

    suspend fun deleteAll() {
        postsUsersDao.deleteAll()
    }
}