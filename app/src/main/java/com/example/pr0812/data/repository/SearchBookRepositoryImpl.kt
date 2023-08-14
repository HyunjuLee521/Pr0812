package com.example.pr0812.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.example.pr0812.data.db.SearchBookDataSource
import com.example.pr0812.data.entity.Book
import com.example.pr0812.domain.repository.ISearchBookRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

open class SearchBookRepositoryImpl @Inject constructor() :
    ISearchBookRepository {

    companion object {
        const val FIRST_PAGE_LOAD_SIZE = 10
    }

    override fun getSearchBook(query: String): Observable<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = FIRST_PAGE_LOAD_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchBookDataSource(query)
            }
        ).observable

    }


}