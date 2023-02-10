package com.ceiba.pruebatecnica.usuarios.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.pruebatecnica.usuarios.databinding.ItemUsersBinding
import com.ceiba.pruebatecnica.usuarios.model.UserModel

class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemUsersBinding.bind(view)

    fun render(
        userModel: UserModel,
        onClickListener: (UserModel) -> Unit,
    ) {
        binding.name.text = userModel.name
        binding.phone.text = userModel.phone
        binding.email.text = userModel.email
        binding.seePosts.setOnClickListener {
            onClickListener(userModel)
        }
    }

}