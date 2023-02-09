package com.ceiba.pruebatecnica.usuarios.viewmodel

import androidx.lifecycle.*
import com.ceiba.pruebatecnica.usuarios.model.UserModel
import com.ceiba.pruebatecnica.usuarios.repository.UsersRepository
import kotlinx.coroutines.launch

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val mutableUsersList = MutableLiveData<ArrayList<UserModel>>()
    val usersList: LiveData<ArrayList<UserModel>> get() = mutableUsersList

    private val mutableUsersError = MutableLiveData<Exception>()
    val usersError: LiveData<Exception> get() = mutableUsersError

    fun getUsers() {
        viewModelScope.launch {
            usersRepository.getUsers({ list ->
                val result = ArrayList<UserModel>()
                list.forEach {
                    result.add(
                        UserModel(
                            it.id,
                            it.name,
                            it.phone,
                            it.email
                        )
                    )
                }
                mutableUsersList.value = result
            }, {
                mutableUsersError.value = it
            })
        }
    }
}

class UsersViewModelFactory(
    private val usersRepository: UsersRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}