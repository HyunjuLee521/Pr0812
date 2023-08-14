package com.example.pr0812.data.db

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.pr0812.data.entity.Book
import com.example.pr0812.data.mapper.toBook
import com.example.pr0812.data.repository.SearchBookRepositoryImpl.Companion.FIRST_PAGE_LOAD_SIZE
import com.example.pr0812.domain.model.ResSearchBook
import com.example.pr0812.domain.repository.NetworkManager.searchBookApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchBookDataSource(
    private val query: String
) : RxPagingSource<Int, Book>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Book>> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return searchBookApi.searchBook(query, position, params.loadSize)
            .subscribeOn(Schedulers.io())
            .map {
                toLoadResult(params, it, position)
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(
        params: LoadParams<Int>,
        data: ResSearchBook,
        position: Int
    ): LoadResult<Int, Book> {

        return LoadResult.Page(
            data = data.documents.map { it.toBook() },
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = if (data.documents.isEmpty() || data.meta.is_end) null else position + (params.loadSize / FIRST_PAGE_LOAD_SIZE)
        )
    }

}