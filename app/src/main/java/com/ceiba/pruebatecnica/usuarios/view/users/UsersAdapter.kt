package com.ceiba.pruebatecnica.usuarios.view.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.pruebatecnica.usuarios.R
import com.ceiba.pruebatecnica.usuarios.model.UserModel

class UsersAdapter(
    private val userList: ArrayList<UserModel>,
    private val onClickListener: (UserModel) -> Unit,
) :
    RecyclerView.Adapter<UsersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UsersViewHolder(
            layoutInflater.inflate(R.layout.item_users, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.render(userList[position], onClickListener)
    }

    override fun getItemCount(): Int = userList.size
}