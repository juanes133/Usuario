package com.ceiba.pruebatecnica.usuarios.view.userposts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.pruebatecnica.usuarios.databinding.ItemUserPostsBinding
import com.ceiba.pruebatecnica.usuarios.model.UserPostsModel

class UserPostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemUserPostsBinding.bind(view)

    fun render(
        userPostsModel: UserPostsModel,
    ) {
        binding.title.text = userPostsModel.title
        binding.body.text = userPostsModel.body
    }
}