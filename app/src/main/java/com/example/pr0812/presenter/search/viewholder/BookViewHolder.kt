package com.example.pr0812.presenter.search.viewholder

import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.pr0812.R
import com.example.pr0812.data.entity.Book
import com.example.pr0812.databinding.ItemBookBinding
import com.example.pr0812.presenter.search.contract.BOOK_COLUMN_COUNT
import com.example.pr0812.presenter.search.viewmodel.SearchViewModel
import com.example.pr0812.presenter.util.getDisplayWidth
import kotlin.math.roundToInt

class BookViewHolder(
    override val vBinding: ItemBookBinding,
    override val vm: SearchViewModel?,
    override val lifecycleOwner: LifecycleOwner?
) : AbstractSearchViewHolder<ItemBookBinding>(vBinding, vm, lifecycleOwner) {

    init {
        val layoutParams = itemView.layoutParams
        layoutParams.width = (getDisplayWidth(itemView.context) / BOOK_COLUMN_COUNT).roundToInt()
        itemView.layoutParams = layoutParams

        itemView.setOnClickListener {
            (it.tag as? Book)?.let { book ->
                vm?.moveToDetail(book)
            }
        }
    }

    fun setData(data: Book) {
        itemView.tag = data

        vBinding.run {
            tvTitle.text = data.title
            ivThumb.run {
                Glide.with(context)
                    .load(data.thumbnail)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(this)
            }
        }

    }
}