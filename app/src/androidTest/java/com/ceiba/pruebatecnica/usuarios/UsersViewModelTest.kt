package com.ceiba.pruebatecnica.usuarios

import androidx.lifecycle.Observer
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import com.ceiba.pruebatecnica.usuarios.database.UsersDb
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersPostsEntity
import com.ceiba.pruebatecnica.usuarios.database.entities.UsersEntity
import com.ceiba.pruebatecnica.usuarios.model.UserPostsModel
import com.ceiba.pruebatecnica.usuarios.model.UserModel
import com.ceiba.pruebatecnica.usuarios.repository.PostsUsersRepository
import com.ceiba.pruebatecnica.usuarios.repository.UsersRepository
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModel
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModelFactory
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UsersViewModelTest {

    var finished = false
    var time = 2

    private var usersViewModel: UsersViewModel
    private var roomDatabase: UsersDb =
        UsersDb.getDatabase(ApplicationProvider.getApplicationContext())
    private var observerUsers: Observer<ArrayList<UserModel>>? = null
    private var observerPosts: Observer<ArrayList<UserPostsModel>>? = null
    private var observerDelete: Observer<Boolean>? = null
    private var observerUsersInsert: Observer<Boolean>? = null
    private var observerUserPostsInsert: Observer<Boolean>? = null

    init {
        //Given
        usersViewModel = UsersViewModelFactory(UsersRepository(roomDatabase.usersDao()),
            PostsUsersRepository(roomDatabase.postsUsersDao())).create(UsersViewModel::class.java)
    }

    @Before
    fun setUp() {
        finished = false
        time = 2
    }

    @After
    fun tearDown() {
        Thread.sleep(time * 1000L)
        Assert.assertTrue(finished)
    }

    @Test
    @UiThreadTest
    fun getUsersTest() {
        //Given
        usersViewModel.deleteData()
        observerDelete = Observer<Boolean> {
            //When
            usersViewModel.getUsers()

            //Then
            observerUsers = Observer<ArrayList<UserModel>> { users ->
                Assert.assertEquals(10, users.size)
                users[0].apply {
                    Assert.assertEquals(1, id)
                    Assert.assertEquals("Leanne Graham", name)
                    Assert.assertEquals("1-770-736-8031 x56442", phone)
                    Assert.assertEquals("Sincere@april.biz", email)
                }
                finished = true
            }
            usersViewModel.usersList.observeForever(observerUsers!!)
        }
        usersViewModel.delete.observeForever(observerDelete!!)
    }

    @Test
    @UiThreadTest
    fun getPostsUserTest() {

        //When
        usersViewModel.deleteData()
        observerDelete = Observer<Boolean> {
            usersViewModel.getUserPosts(1)

            //Then
            observerPosts = Observer<ArrayList<UserPostsModel>> { posts ->
                Assert.assertEquals(10, posts.size)
                posts[0].apply {
                    Assert.assertEquals(1, userId)
                    Assert.assertEquals(1, id)
                    Assert.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                        title)
                    Assert.assertEquals("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum"
                            + "\nreprehenderit molestiae ut ut quas totam"
                            + "\nnostrum rerum est autem sunt rem eveniet architecto",
                        body)
                }
                finished = true
            }
            usersViewModel.userPostsList.observeForever(observerPosts!!)
        }
        usersViewModel.delete.observeForever(observerDelete!!)
    }

    @Test
    @UiThreadTest
    fun getCachedUsersTest() {
        //When (action)
        usersViewModel.deleteData()
        observerDelete = Observer<Boolean> {
            //inserta un usuario dummy en la base de datos
            val list = ArrayList<UsersEntity>()
            list.add(UsersEntity(1, "Leanne Graham", "1-770-736-8031 x56442", "Sincere@april.biz"))
            usersViewModel.insertUsersCache(list)
            observerUsersInsert = Observer<Boolean> {
                //When
                //trae los usuarios de la base de datos para validar que si se hayan cacheado
                usersViewModel.getUsersCache()

                //Then (assertion)
                observerUsers = Observer<ArrayList<UserModel>> { users ->
                    Assert.assertEquals(1, users.size)
                    users[0].apply {
                        Assert.assertEquals(1, id)
                        Assert.assertEquals("Leanne Graham", name)
                        Assert.assertEquals("1-770-736-8031 x56442", phone)
                        Assert.assertEquals("Sincere@april.biz", email)
                    }
                    finished = true
                }
                usersViewModel.usersList.observeForever(observerUsers!!)
            }
            usersViewModel.insertUsersCache.observeForever(observerUsersInsert!!)
        }
        usersViewModel.delete.observeForever(observerDelete!!)
    }

    @Test
    @UiThreadTest
    fun getPostsUsersCacheTests() {
        //When (action)
        usersViewModel.deleteData()
        observerDelete = Observer<Boolean> {
            //inserta un usuario dummy en la base de datos
            val list = ArrayList<UsersPostsEntity>()
            list.add(UsersPostsEntity(1,
                1,
                "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum"
                        + "\nreprehenderit molestiae ut ut quas totam"
                        + "\nnostrum rerum est autem sunt rem eveniet architecto"))
            usersViewModel.insertUserPostsCache(list)
            observerUserPostsInsert = Observer<Boolean> {
                //When
                //trae los usuarios de la base de datos para validar que si se hayan cacheado
                usersViewModel.getUserPostsCache(1)

                //Then (assertion)
                observerPosts = Observer<ArrayList<UserPostsModel>> { posts ->
                    Assert.assertEquals(1, posts.size)
                    posts[0].apply {
                        Assert.assertEquals(1, userId)
                        Assert.assertEquals(1, id)
                        Assert.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", title)
                        Assert.assertEquals("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum"
                                + "\nreprehenderit molestiae ut ut quas totam"
                                + "\nnostrum rerum est autem sunt rem eveniet architecto", body)
                    }
                    finished = true
                }
                usersViewModel.userPostsList.observeForever(observerPosts!!)
            }
            usersViewModel.insertUserPostsCache.observeForever(observerUserPostsInsert!!)
        }
        usersViewModel.delete.observeForever(observerDelete!!)
    }
}