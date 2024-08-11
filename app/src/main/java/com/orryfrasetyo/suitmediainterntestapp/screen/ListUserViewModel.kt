package com.orryfrasetyo.suitmediainterntestapp.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orryfrasetyo.suitmediainterntestapp.api.ApiConfig
import com.orryfrasetyo.suitmediainterntestapp.api.ApiService
import com.orryfrasetyo.suitmediainterntestapp.response.ListUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserViewModel: ViewModel() {

    private val _userResponse = MutableLiveData<ListUserResponse>()
    val userResponse: LiveData<ListUserResponse> get() = _userResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var currentPage = 1

    private val apiService = ApiConfig.retrofit.create(ApiService::class.java)

    fun getUsers() {
        val call = apiService.getUsers(page = 1, perPage = 12)

        call.enqueue(object : Callback<ListUserResponse> {
            override fun onResponse(call: Call<ListUserResponse>, response: Response<ListUserResponse>) {
                if (response.isSuccessful) {
                    _userResponse.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                _error.value = "Failure: ${t.message}"
            }
        })
    }

    fun resetPage() {
        currentPage = 1
    }
}










