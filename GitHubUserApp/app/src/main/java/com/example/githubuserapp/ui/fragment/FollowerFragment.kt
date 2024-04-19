package com.example.githubuserapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.FragmentFollowerBinding
import com.example.githubuserapp.ui.adapter.FollowerAdapter
import com.example.githubuserapp.ui.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {
    private val listData: ArrayList<User> = ArrayList()
    private lateinit var adapter: FollowerAdapter
    private lateinit var followerViewModel: FollowerViewModel

    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()

        val dataUser = requireActivity().intent.getParcelableExtra<User>(EXTRA_USER)!!
        config()

        followerViewModel.getDataGit(
            requireActivity().applicationContext,
            dataUser.username.toString()
        )
        if (dataUser.followers == "0") {
            showLoading(false)
            errorMessage(true)
        } else {
            showLoading(true)
        }

        followerViewModel.getListFollower().observe(requireActivity(), { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun initializeViewModel(){
        adapter = FollowerAdapter(listData)
        followerViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)
    }

    private fun config() {
        binding.recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewFollowers.setHasFixedSize(true)
        binding.recyclerViewFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressbarFollowers.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    private fun errorMessage(state: Boolean) {
        if (state) {
            binding.cvErrorMessage.visibility = View.VISIBLE
        } else {
            binding.cvErrorMessage.visibility = View.INVISIBLE
        }
    }

    companion object {
        val TAG = FollowerFragment::class.java.simpleName
        const val EXTRA_USER = "extra_user"
    }
}