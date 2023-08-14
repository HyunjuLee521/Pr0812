package com.example.pr0812.domain.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pr0812.data.entity.Book
import io.reactivex.rxjava3.core.Observable
import javax.inject.Singleton

@Singleton
interface ISearchBookRepository {
    fun getSearchBook(query: String): Observable<PagingData<Book>>
}