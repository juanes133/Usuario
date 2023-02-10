package com.ceiba.pruebatecnica.usuarios.repository

import com.ceiba.pruebatecnica.usuarios.database.daos.UsersDao
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersEntity
import com.ceiba.pruebatecnica.usuarios.repository.base.BaseRepository

class UsersRepository(private val usersDao: UsersDao) : BaseRepository() {

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
                        it.email
                    )
                )
            }
            onSuccess(result)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    suspend fun getUsersCache(): ArrayList<UsersEntity> {
        return usersDao.getAll() as ArrayList<UsersEntity>
    }

    suspend fun insertUsersCache(list: ArrayList<UsersEntity>) {
        usersDao.insertAll(list)
    }

    suspend fun deleteAll() {
        usersDao.deleteAll()
    }
}
