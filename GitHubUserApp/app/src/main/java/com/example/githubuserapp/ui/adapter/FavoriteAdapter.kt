package com.example.githubuserapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ItemUserBinding
import com.example.githubuserapp.ui.activity.DetailUserActivity

class FavouriteAdapter :
    RecyclerView.Adapter<FavouriteAdapter.ListViewHolder>() {

    var listFavourite = ArrayList<User>()
        set(listFavourite) {
            if (listFavourite.size > 0) {
                this.listFavourite.clear()
            }
            this.listFavourite.addAll(listFavourite)
            notifyDataSetChanged()
        }

    inner class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: User) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .circleCrop()
                    .into(ivItemAvatar)

                tvItemName.text = favorite.name
                tvItemUsername.text = favorite.username
                tvItemCompany.text = favorite.company
                countRepository.text =
                    itemView.context.getString(R.string.repository, favorite.repository)
                countFollowers.text =
                    itemView.context.getString(R.string.follower, favorite.followers)
                countFollowing.text =
                    itemView.context.getString(R.string.follower, favorite.following)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavourite.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFavourite[position])

        val data = listFavourite[position]
        holder.itemView.setOnClickListener {

            val dataUserIntent = User(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following,
            )
            val mIntent = Intent(it.context, DetailUserActivity::class.java)
            mIntent.putExtra(DetailUserActivity.EXTRA_USER, dataUserIntent)
            it.context.startActivity(mIntent)
        }

    }
}