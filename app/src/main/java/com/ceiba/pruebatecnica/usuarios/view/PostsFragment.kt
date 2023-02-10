package com.ceiba.pruebatecnica.usuarios.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.ceiba.pruebatecnica.usuarios.databinding.FragmentPostsBinding

class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }
}