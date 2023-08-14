package com.example.pr0812.presenter.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pr0812.data.entity.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?>
        get() = _book

    fun setDetailData(book: Book?) {
        _book.value = book
    }

    override fun onCleared() {
        super.onCleared()
    }
}