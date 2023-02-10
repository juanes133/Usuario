package com.ceiba.pruebatecnica.usuarios.view.users

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceiba.pruebatecnica.usuarios.UsersApplication
import com.ceiba.pruebatecnica.usuarios.databinding.FragmentUsersBinding
import com.ceiba.pruebatecnica.usuarios.model.UserModel
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModel
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModelFactory
import java.util.*
import kotlin.collections.ArrayList


class UsersFragment : Fragment() {

    private var userListToFilter: ArrayList<UserModel> = ArrayList()
    private lateinit var binding: FragmentUsersBinding
    private val usersViewModel: UsersViewModel by viewModels {
        UsersViewModelFactory(
            (activity?.application as UsersApplication).usersRepository,
            (activity?.application as UsersApplication).postsUsersRepository,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        usersViewModel.usersList.observe(viewLifecycleOwner) {
            userListToFilter = it
            showUserList(it)
        }
        binding.btnRetry.setOnClickListener {
            getUsers()
        }
        getUsers()
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                if (s.isNotEmpty()) {
                    val userFilter = userListToFilter.filter { x ->
                        x.name.lowercase().startsWith(s.toString().lowercase(Locale.ROOT))
                    } as ArrayList<UserModel>
                    if (userFilter.isNotEmpty()) {
                        showUserList(
                            userFilter
                        )
                    } else {
                        showUserList(ArrayList())
                        binding.textListEmpty.isVisible = true
                    }
                } else {
                    showUserList(userListToFilter)
                }
            }
        })
        return binding.root
    }

    private fun showUserList(userList: ArrayList<UserModel>) {
        initUsersRecyclerView(userList)
        binding.loading.isVisible = false
        binding.usersContainer.isVisible = true
        binding.fallbackContainer.isVisible = false
        binding.textListEmpty.isVisible = false
    }

    private fun getUsers() {
        binding.usersContainer.isVisible = false
        binding.fallbackContainer.isVisible = false
        binding.loading.isVisible = true
        usersViewModel.getUsers()
    }

    private fun initUsersRecyclerView(list: ArrayList<UserModel>) {
        binding.recyclerUser.layoutManager = LinearLayoutManager(context)
        binding.recyclerUser.adapter = UsersAdapter(list) {
            findNavController().navigate(
                UsersFragmentDirections.actionUserFragmentToPostsFragment(
                    it.id,
                    it.name,
                    it.phone,
                    it.email
                )
            )
        }
    }
}