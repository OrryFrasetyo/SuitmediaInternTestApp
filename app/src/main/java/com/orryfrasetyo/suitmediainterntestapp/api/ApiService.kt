package com.orryfrasetyo.suitmediainterntestapp.api

import com.orryfrasetyo.suitmediainterntestapp.response.ListUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<ListUserResponse>
}