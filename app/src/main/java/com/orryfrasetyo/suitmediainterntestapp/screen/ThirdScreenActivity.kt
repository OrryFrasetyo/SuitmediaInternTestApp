package com.orryfrasetyo.suitmediainterntestapp.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orryfrasetyo.suitmediainterntestapp.R
import com.orryfrasetyo.suitmediainterntestapp.adapter.UserAdapter
import com.orryfrasetyo.suitmediainterntestapp.databinding.ActivityThirdScreenBinding
import com.orryfrasetyo.suitmediainterntestapp.response.DataItem

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var adapter: UserAdapter
    private val viewModel by viewModels<ListUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.resetPage()
            viewModel.getUsers()
        }

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (!binding.swipeRefreshLayout.isRefreshing && lastVisibleItemPosition == totalItemCount - 1) {
                    viewModel.getUsers()
                }
            }
        })

        viewModel.userResponse.observe(this) { userResponse ->
            if (userResponse != null) {
                userResponse.data?.let { setReviewData(it) }
                showLoading(false)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                showLoading(false)
            }
        }

        adapter = UserAdapter { selectedUser ->
            val intent = Intent()
            intent.putExtra("selectedUserName", "${selectedUser.firstName} ${selectedUser.lastName}")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.rvList.adapter = adapter
        viewModel.getUsers()
        showLoading(true)

        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    private fun setReviewData(users: List<DataItem?>) {
        if (users.isNotEmpty()) {
            adapter.submitList(users)
            binding.rvList.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.GONE
        } else {
            binding.rvList.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}









