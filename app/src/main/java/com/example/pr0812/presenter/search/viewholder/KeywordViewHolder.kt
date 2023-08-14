package com.example.pr0812.presenter.search.viewholder

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pr0812.data.entity.Keyword
import com.example.pr0812.databinding.ItemKeywordBinding
import com.example.pr0812.presenter.search.adapter.BookAdapter
import com.example.pr0812.presenter.search.contract.BOOK_ITEM_CACHE_SIZE
import com.example.pr0812.presenter.search.contract.KEYWORD_ROW_COUNT
import com.example.pr0812.presenter.search.viewmodel.SearchViewModel
import com.example.pr0812.presenter.util.SpaceItemDecoration
import com.example.pr0812.presenter.util.disableAnimation
import com.example.pr0812.presenter.util.dpToPx
import com.example.pr0812.presenter.util.getDisplayHeight
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class KeywordViewHolder(
    override val vBinding: ItemKeywordBinding,
    override val vm: SearchViewModel,
    override val lifecycleOwner: LifecycleOwner,
    private val bookRvPool: RecyclerView.RecycledViewPool
) : AbstractSearchViewHolder<ItemKeywordBinding>(vBinding, vm, lifecycleOwner) {

    private var bookAdapter: BookAdapter = BookAdapter(vm, lifecycleOwner)


    init {
        val layoutParams = itemView.layoutParams
        layoutParams.height = getDisplayHeight(itemView.context) / KEYWORD_ROW_COUNT
        itemView.layoutParams = layoutParams

        vBinding.rvBook.run {
            layoutManager = LinearLayoutManager(itemView.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            addItemDecoration(SpaceItemDecoration(dpToPx(itemView.context, 8)))

            setHasFixedSize(true)
            setRecycledViewPool(bookRvPool)
            disableAnimation(this)
            setItemViewCacheSize(BOOK_ITEM_CACHE_SIZE)

            adapter = bookAdapter
        }

        vm.booksSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (itemView.tag as? Keyword?)?.let { tag ->
                    if (tag == it.first) {
                        bookAdapter.submitData(lifecycleOwner.lifecycle, it.second)
                    }
                }
            }, {

            })

    }

    fun setData(keyword: Keyword) {
        itemView.tag = keyword
        vBinding.tvKeyword.text = keyword.query
        vm.loadBooks(keyword)
    }
}