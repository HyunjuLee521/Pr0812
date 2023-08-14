package com.example.pr0812.presenter.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.pr0812.data.entity.Book
import com.example.pr0812.databinding.ItemBookBinding
import com.example.pr0812.presenter.search.viewholder.BookViewHolder
import com.example.pr0812.presenter.search.viewmodel.SearchViewModel

class BookAdapter(
    private val viewModel: SearchViewModel?,
    private val lifecycleOwner: LifecycleOwner?
) : PagingDataAdapter<Book, BookViewHolder>(DiffUtil) {

    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, viewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let {
            holder.setData(it)
        }
    }



}