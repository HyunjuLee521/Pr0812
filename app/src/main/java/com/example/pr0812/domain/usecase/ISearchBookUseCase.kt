package com.example.pr0812.domain.usecase

import androidx.paging.PagingData
import com.example.pr0812.data.entity.Book
import com.example.pr0812.data.entity.Keyword
import io.reactivex.rxjava3.core.Observable
import javax.inject.Singleton

@Singleton
interface ISearchBookUseCase {
    fun invoke(keyword: Keyword): Observable<PagingData<Book>>
}