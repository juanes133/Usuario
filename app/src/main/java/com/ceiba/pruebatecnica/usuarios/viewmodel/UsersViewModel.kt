package com.ceiba.pruebatecnica.usuarios.viewmodel

import androidx.lifecycle.*
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersPostsEntity
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersEntity
import com.ceiba.pruebatecnica.usuarios.model.UserPostsModel
import com.ceiba.pruebatecnica.usuarios.model.UserModel
import com.ceiba.pruebatecnica.usuarios.repository.PostsUsersRepository
import com.ceiba.pruebatecnica.usuarios.repository.UsersRepository
import kotlinx.coroutines.launch

class UsersViewModel(
    private val usersRepository: UsersRepository,
    private val postsUsersRepository: PostsUsersRepository,
) : ViewModel() {

    private val mutableUsersList = MutableLiveData<ArrayList<UserModel>>()
    val usersList: LiveData<ArrayList<UserModel>> get() = mutableUsersList

    private val mutablePostsUsersList = MutableLiveData<ArrayList<UserPostsModel>>()
    val userPostsList: LiveData<ArrayList<UserPostsModel>> get() = mutablePostsUsersList

    private val mutableDelete = MutableLiveData<Boolean>()
    val delete: LiveData<Boolean> get() = mutableDelete

    private val mutableUsersInsertCache = MutableLiveData<Boolean>()
    val insertUsersCache: LiveData<Boolean> get() = mutableUsersInsertCache

    private val mutableUserPostsInsertCache = MutableLiveData<Boolean>()
    val insertUserPostsCache: LiveData<Boolean> get() = mutableUserPostsInsertCache

    private val mutableUsersError = MutableLiveData<Exception>()
    val usersError: LiveData<Exception> get() = mutableUsersError

    fun getUsers() {
        viewModelScope.launch {
            val cachedUsers = usersRepository.getUsersCache()
            if (cachedUsers.size > 0) {
                mutableUsersList.value = toUserModelList(cachedUsers)
            } else {
                usersRepository.getUsers({ list ->
                    insertUsersCache(list)
                    mutableUsersList.value = toUserModelList(list)
                }, {
                    mutableUsersError.value = it
                })
            }
        }
    }

    fun insertUsersCache(list: ArrayList<UsersEntity>) {
        viewModelScope.launch {
            usersRepository.insertUsersCache(list)
            mutableUsersInsertCache.value = true
        }
    }

    fun getUserPosts(id: Int) {
        viewModelScope.launch {
            val cachedPosts = postsUsersRepository.getUserPostsCache(id)
            if (cachedPosts.size > 0) {
                mutablePostsUsersList.value = toUserPostsModelList(cachedPosts)
            } else {
                postsUsersRepository.getUserPosts(id, { list ->
                    insertUserPostsCache(list)
                    mutablePostsUsersList.value = toUserPostsModelList(list)
                }, {
                    mutableUsersError.value = it
                })
            }
        }
    }

    fun insertUserPostsCache(list: ArrayList<UsersPostsEntity>) {
        viewModelScope.launch {
            postsUsersRepository.insertUserPostsCache(list)
            mutableUserPostsInsertCache.value = true
        }
    }

    fun deleteData() {
        viewModelScope.launch {
            usersRepository.deleteAll()
            postsUsersRepository.deleteAll()
            mutableDelete.value = true
        }
    }

    fun getUsersCache() {
        viewModelScope.launch {
            mutableUsersList.value = toUserModelList(usersRepository.getUsersCache())
        }
    }

    private fun toUserModelList(users: ArrayList<UsersEntity>): ArrayList<UserModel> {
        val result = ArrayList<UserModel>()
        users.forEach {
            result.add(
                UserModel(
                    it.id,
                    it.name,
                    it.phone,
                    it.email
                )
            )
        }
        return result
    }

    private fun toUserPostsModelList(posts: ArrayList<UsersPostsEntity>): ArrayList<UserPostsModel> {
        val result = ArrayList<UserPostsModel>()
        posts.forEach {
            result.add(
                UserPostsModel(
                    it.userId,
                    it.id,
                    it.title,
                    it.body
                )
            )
        }
        return result
    }

    fun getUserPostsCache(id: Int) {
        viewModelScope.launch {
            mutablePostsUsersList.value = toUserPostsModelList(postsUsersRepository.getUserPostsCache(id))
        }
    }
}

class UsersViewModelFactory(
    private val usersRepository: UsersRepository,
    private val postsUsersRepository: PostsUsersRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(usersRepository, postsUsersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}