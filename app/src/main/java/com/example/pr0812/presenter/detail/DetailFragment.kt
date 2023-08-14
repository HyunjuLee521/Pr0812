package com.example.pr0812.presenter.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.pr0812.R
import com.example.pr0812.data.entity.Book
import com.example.pr0812.databinding.FragmentDetailBinding
import com.example.pr0812.presenter.detail.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var vBinding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private var book: Book? = null
    private val bookObserver = Observer<Book?> {
        initView(it)
    }

    companion object {
        fun newInstance(book: Book?): DetailFragment {
            return DetailFragment().apply {
                this.book = book
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
    }

    private fun observeData() {
        detailViewModel.book.observe(this as LifecycleOwner, bookObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.setDetailData(book = book)
    }


    private fun initView(book: Book?) {
        vBinding.run {
            ivThumbnail.run {
                Glide.with(context)
                    .load(book?.thumbnail ?: "")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(this)
            }
            tvTitle.text = "제목 : ${book?.title}"
            tvAuthor.text = "저자 : ${book?.author}"
            tvDateTime.text = "출간일 : ${book?.dateTime}"
            tvPrice.text = "가격 : ${book?.price}"
            tvContents.text = "내용 : ${book?.contents}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailViewModel.book.removeObserver(bookObserver)
    }

}