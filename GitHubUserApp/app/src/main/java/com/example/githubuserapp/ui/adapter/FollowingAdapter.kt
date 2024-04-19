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

class FollowingAdapter (private val listFollowing: ArrayList<User>) :
    RecyclerView.Adapter<FollowingAdapter.ListDataHolder>() {

    fun setData(item: ArrayList<User>) {
        listFollowing.clear()
        listFollowing.addAll(item)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataFollowing: User) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(dataFollowing.avatar)
                    .circleCrop()
                    .into(ivItemAvatar)

                tvItemName.text = dataFollowing.name
                tvItemUsername.text = dataFollowing.username
                tvItemCompany.text = dataFollowing.company
                countRepository.text =
                    itemView.context.getString(R.string.repository, dataFollowing.repository)
                countFollowers.text =
                    itemView.context.getString(R.string.follower, dataFollowing.followers)
                countFollowing.text =
                    itemView.context.getString(R.string.follower, dataFollowing.following)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListDataHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDataHolder(binding)

    }

    override fun getItemCount(): Int {
        return listFollowing.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listFollowing[position])

        val data = listFollowing[position]

        holder.itemView.setOnClickListener {
            val dataUserIntent = User(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, DetailUserActivity::class.java)
            mIntent.putExtra(DetailUserActivity.EXTRA_USER, dataUserIntent)
            it.context.startActivity(mIntent)
        }
    }
}