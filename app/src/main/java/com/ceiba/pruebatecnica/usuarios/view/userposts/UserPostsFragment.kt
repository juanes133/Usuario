package com.ceiba.pruebatecnica.usuarios.view.userposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceiba.pruebatecnica.usuarios.UsersApplication
import com.ceiba.pruebatecnica.usuarios.databinding.FragmentUserPostsBinding
import com.ceiba.pruebatecnica.usuarios.model.UserPostsModel
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModel
import com.ceiba.pruebatecnica.usuarios.viewmodel.UsersViewModelFactory

class UserPostsFragment : Fragment() {

    private lateinit var binding: FragmentUserPostsBinding
    private val args: UserPostsFragmentArgs by navArgs()
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
    ): View? {
        binding = FragmentUserPostsBinding.inflate(inflater, container, false)
        usersViewModel.userPostsList.observe(viewLifecycleOwner) {
            initUserPostsRecyclerView(it)
            binding.name.text = args.name
            binding.phone.text = args.phone
            binding.email.text = args.email
            binding.loading.isVisible = false
            binding.userPostsContainer.isVisible = true
            binding.fallbackContainer.isVisible = false
        }
        binding.btnRetry.setOnClickListener {
            getUserPosts()
        }
        getUserPosts()
        return binding.root
    }

    private fun getUserPosts() {
        binding.userPostsContainer.isVisible = false
        binding.fallbackContainer.isVisible = false
        binding.loading.isVisible = true
        usersViewModel.getUserPosts(args.iduser)
    }

    private fun initUserPostsRecyclerView(list: ArrayList<UserPostsModel>) {
        binding.recyclerUserPosts.layoutManager = LinearLayoutManager(context)
        binding.recyclerUserPosts.adapter = UserPostsAdapter(list)
    }
}