package com.ceiba.pruebatecnica.usuarios.view

import android.os.Bundle
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

class UsersFragment : Fragment() {

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
            initRecyclerView(it)
            binding.loading.isVisible = false
            binding.usersContainer.isVisible = true
            binding.fallbackContainer.isVisible = false
        }
        binding.btnRetry.setOnClickListener {
            getUsers()
        }
        getUsers()
        return binding.root
    }

    private fun getUsers() {
        binding.usersContainer.isVisible = false
        binding.fallbackContainer.isVisible = false
        binding.loading.isVisible = true
        usersViewModel.getUsers()
    }

    private fun initRecyclerView(list: ArrayList<UserModel>) {
        binding.recyclerUser.layoutManager = LinearLayoutManager(context)
        binding.recyclerUser.adapter = UsersAdapter(list) {
            findNavController().navigate(UsersFragmentDirections.actionUserFragmentToPostsFragment(
                it.id.toString()))
        }
    }
}