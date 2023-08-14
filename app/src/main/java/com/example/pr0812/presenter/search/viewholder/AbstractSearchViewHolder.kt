package com.example.pr0812.presenter.search.viewholder

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.pr0812.presenter.search.viewmodel.SearchViewModel

abstract class AbstractSearchViewHolder<VDB : ViewBinding>(
    open val vBinding: VDB, open val vm: SearchViewModel?, open val lifecycleOwner: LifecycleOwner?
) : RecyclerView.ViewHolder(vBinding.root)