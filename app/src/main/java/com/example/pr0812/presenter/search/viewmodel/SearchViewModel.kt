package com.example.pr0812.presenter.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.pr0812.data.entity.Book
import com.example.pr0812.data.entity.Keyword
import com.example.pr0812.domain.usecase.ISearchBookUseCase
import com.example.pr0812.presenter.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val iSearchBookUseCase: ISearchBookUseCase
) :
    ViewModel() {

    private val _keywords = SingleLiveEvent<ArrayList<Keyword>>()
    val keywords: LiveData<ArrayList<Keyword>>
        get() = _keywords

    private var disposable: Disposable? = null
    val booksSubject: PublishSubject<Pair<Keyword, PagingData<Book>>> = PublishSubject.create()

    private val _moveToDetailAction = SingleLiveEvent<Book>()
    val moveToDetailAction: LiveData<Book>
        get() = _moveToDetailAction

    init {
        _keywords.value = arrayListOf()
    }

    fun addKeyword(query: String) {
        _keywords.value = getKeywordList(query = query)
    }

    fun syncKeyword() {
        val currentKeyword = _keywords.value
        _keywords.value = currentKeyword ?: arrayListOf()
    }

    fun loadBooks(keyword: Keyword) {
        disposable = iSearchBookUseCase.invoke(keyword = keyword)
            .subscribeOn(Schedulers.io())
            .subscribe({
                booksSubject.onNext(Pair(keyword, it))
            }, {

            })
    }

    fun moveToDetail(book: Book) {
        _moveToDetailAction.value = book
    }

    private fun getKeywordList(query: String): ArrayList<Keyword> {
        val currentKeywords = _keywords.value
        val newKeyword = Keyword(System.currentTimeMillis().toString(), query)

        return arrayListOf<Keyword>().apply {
            addAll(ArrayList(currentKeywords))
            add(newKeyword)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}