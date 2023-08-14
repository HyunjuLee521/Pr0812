package com.example.pr0812.domain.repository

import com.example.pr0812.domain.model.ResSearchBook
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchBookApi {

    @GET("v3/search/book.json")
    fun searchBook(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<ResSearchBook>

}