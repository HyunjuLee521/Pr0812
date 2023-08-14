package com.example.pr0812.domain.usecase

import androidx.paging.PagingData
import com.example.pr0812.data.entity.Book
import com.example.pr0812.data.entity.Keyword
import com.example.pr0812.domain.repository.ISearchBookRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchBookUseCaseImpl @Inject constructor(
    private val iSearchBookRepository: ISearchBookRepository
) : ISearchBookUseCase {

    override fun invoke(keyword: Keyword): Observable<PagingData<Book>> {
        return iSearchBookRepository.getSearchBook(
            query = keyword.query
        )
    }

}