package com.example.pr0812.presenter.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pr0812.R
import com.example.pr0812.data.entity.Book
import com.example.pr0812.data.entity.Keyword
import com.example.pr0812.databinding.FragmentSearchBinding
import com.example.pr0812.presenter.detail.DetailFragment
import com.example.pr0812.presenter.search.adapter.KeywordAdapter
import com.example.pr0812.presenter.search.contract.KEYWORD_ITEM_CACHE_SIZE
import com.example.pr0812.presenter.search.viewmodel.SearchViewModel
import com.example.pr0812.presenter.util.disableAnimation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var vBinding: FragmentSearchBinding
    private lateinit var keywordAdapter: KeywordAdapter

    // observer
    private val keywordsObserver = Observer<ArrayList<Keyword>> {
        keywordAdapter.submitList(it) {
            scrollToPosition(it.lastIndex)
        }
    }
    private val moveToDetailActionObserver = Observer<Book> {
        moveToDetail(book = it)
    }

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
    }

    private fun observeData() {
        searchViewModel.run {
            keywords.removeObserver(keywordsObserver)
            keywords.observe(this@SearchFragment as LifecycleOwner, keywordsObserver)
            syncKeyword()

            moveToDetailAction.observe(
                this@SearchFragment as LifecycleOwner,
                moveToDetailActionObserver
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        vBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        keywordAdapter = KeywordAdapter(searchViewModel, viewLifecycleOwner)

        vBinding.rvKeyword.run {
            layoutManager = LinearLayoutManager(context).apply {
                reverseLayout = true
                stackFromEnd = true
            }

            setHasFixedSize(true)
            setItemViewCacheSize(KEYWORD_ITEM_CACHE_SIZE)
            disableAnimation(this)

            adapter = keywordAdapter
        }

        vBinding.btnSearch.setOnClickListener {
            val query = vBinding.etSearch.text.toString()
            if (query.isNotBlank()) {
                searchViewModel.addKeyword(query)
                vBinding.etSearch.text.clear()
            }
        }

    }

    private fun scrollToPosition(position: Int) {
        (vBinding.rvKeyword.layoutManager as? LinearLayoutManager)?.scrollToPosition(position)
    }

    private fun moveToDetail(book: Book) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.navHost, DetailFragment.newInstance(book = book))
            ?.addToBackStack(SearchFragment::class.java.name)?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchViewModel.run {
            keywords.removeObserver(keywordsObserver)
            moveToDetailAction.removeObserver(moveToDetailActionObserver)
        }
    }


}