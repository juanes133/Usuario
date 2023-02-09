package com.ceiba.pruebatecnica.usuarios

import androidx.lifecycle.Observer
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import com.ceiba.pruebatecnica.usuarios.database.UsersDb
import com.ceiba.pruebatecnica.usuarios.model.UserModel
import com.ceiba.pruebatecnica.usuarios.repository.UsersRepository
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModel
import org.junit.Assert
import org.junit.Test

class UsersViewModelTest : BaseUnitTests() {

    private var roomDatabase: UsersDb =
        UsersDb.getDatabase(ApplicationProvider.getApplicationContext())
    private var observer: Observer<ArrayList<UserModel>>? = null

    @Test
    @UiThreadTest
    fun getUsersTest() {
        //Given
        val usersViewModel = UsersViewModel(UsersRepository(roomDatabase.usersDao()))

        //When
        usersViewModel.getUsers()

        //Then
        observer = Observer<ArrayList<UserModel>> { users ->
            Assert.assertEquals(10, users.size)
            users[0].apply {
                Assert.assertEquals(1, id)
                Assert.assertEquals("Leanne Graham", name)
                Assert.assertEquals("1-770-736-8031 x56442", phone)
                Assert.assertEquals("Sincere@april.biz", email)
            }
            finished = true
        }
        usersViewModel.usersList.observeForever(observer!!)

    }
}