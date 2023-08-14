package com.example.pr0812.presenter.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pr0812.data.entity.Keyword
import com.example.pr0812.databinding.ItemKeywordBinding
import com.example.pr0812.presenter.search.viewholder.KeywordViewHolder
import com.example.pr0812.presenter.search.viewmodel.SearchViewModel

class KeywordAdapter(
    private var viewModel: SearchViewModel,
    private var lifecycleOwner: LifecycleOwner
) : ListAdapter<Keyword, KeywordViewHolder>(
    diffUtil
) {

    private val rvBookPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Keyword>() {
            override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.query == newItem.query
            }

            override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val binding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeywordViewHolder(binding, viewModel, lifecycleOwner, rvBookPool)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        getItem(position)?.let {
            holder.setData(it)
        }
    }
}