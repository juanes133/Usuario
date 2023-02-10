package com.ceiba.pruebatecnica.usuarios

import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ceiba.pruebatecnica.usuarios.database.UsersDb
import com.ceiba.pruebatecnica.usuarios.repository.UserPostsRepository
import com.ceiba.pruebatecnica.usuarios.repository.UsersRepository
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModel
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModelFactory
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UsersEspressoTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    private var usersViewModel: UsersViewModel
    private var roomDatabase: UsersDb =
        UsersDb.getDatabase(ApplicationProvider.getApplicationContext())
    init {
        //Given
        usersViewModel = UsersViewModelFactory(
            UsersRepository(roomDatabase.usersDao()),
            UserPostsRepository(roomDatabase.postsUsersDao())
        ).create(UsersViewModel::class.java)
    }

    @Test
    fun usersFragmentTest() {
        usersViewModel.deleteData()
        Thread.sleep(2000)
        onView(
            allOf(
                withText("Buscar usuario"),
                isDisplayed()
            )
        ).check(matches(withText("Buscar usuario")))

        onView(
            allOf(
                withId(R.id.search),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withText("Leanne Graham"),
                isDisplayed()
            )
        ).check(matches(withText("Leanne Graham")))

        onView(
            allOf(
                withText("1-770-736-8031 x56442"),
                isDisplayed()
            )
        ).check(matches(withText("1-770-736-8031 x56442")))

        onView(
            allOf(
                withText("Sincere@april.biz"),
                isDisplayed()
            )
        ).check(matches(withText("Sincere@april.biz")))

        onView(
            allOf(
                withIndex(withId(R.id.iconPhone), 0),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withIndex(withId(R.id.iconEmail), 0),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

       onView(
            allOf(
                withIndex(withId(R.id.seePosts), 0), withText("VER PUBLICACIONES"),
                isDisplayed()
            )
        ).perform(ViewActions.click())

        Thread.sleep(2000L)

        onView(
            allOf(
                withText("Leanne Graham"),
                isDisplayed()
            )
        ).check(matches(withText("Leanne Graham")))

        onView(
            allOf(
                withText("1-770-736-8031 x56442"),
                isDisplayed()
            )
        ).check(matches(withText("1-770-736-8031 x56442")))

        onView(
            allOf(
                withIndex(withId(R.id.iconPhone), 0),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withText("Sincere@april.biz"),
                isDisplayed()
            )
        ).check(matches(withText("Sincere@april.biz")))

        onView(
            allOf(
                withIndex(withId(R.id.iconEmail), 0),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withIndex(withId(R.id.title), 0),
                withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"),
                isDisplayed()
            )
        ).check(matches(withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")))

        onView(
            allOf(
                withIndex(withId(R.id.body), 0),
                withText(
                    "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum"
                            + "\nreprehenderit molestiae ut ut quas totam"
                            + "\nnostrum rerum est autem sunt rem eveniet architecto"
                ),
                isDisplayed()
            )
        ).check(matches(withText("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum"
                + "\nreprehenderit molestiae ut ut quas totam"
                + "\nnostrum rerum est autem sunt rem eveniet architecto")))
    }

    private fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}