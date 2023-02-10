package com.ceiba.pruebatecnica.usuarios.view.userposts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.pruebatecnica.usuarios.R
import com.ceiba.pruebatecnica.usuarios.model.UserPostsModel

class UserPostsAdapter(

    private val userPostsList: ArrayList<UserPostsModel>,
) :
    RecyclerView.Adapter<UserPostsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserPostsViewHolder(
            layoutInflater.inflate(R.layout.item_user_posts, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserPostsViewHolder, position: Int) {
        holder.render(userPostsList[position])
    }

    override fun getItemCount(): Int = userPostsList.size
}