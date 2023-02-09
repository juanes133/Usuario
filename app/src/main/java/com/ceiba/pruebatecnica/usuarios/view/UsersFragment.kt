package com.ceiba.pruebatecnica.usuarios.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
            (activity?.application as UsersApplication).usersRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        val list = ArrayList<UserModel>()
        list.add(UserModel(1, "Leanne Graham", "4-236737-6373673", "juasnkbnsdb@.com"))
        initRecyclerView(list)
        return binding.root
    }

    private fun initRecyclerView(list: ArrayList<UserModel>) {
        binding.recyclerUser.layoutManager = LinearLayoutManager(context)
        binding.recyclerUser.adapter = UsersAdapter(list, {})
    }
}