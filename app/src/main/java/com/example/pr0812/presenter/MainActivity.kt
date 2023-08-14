package com.example.pr0812.presenter

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.pr0812.R
import com.example.pr0812.presenter.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchFragment = supportFragmentManager.findFragmentById(R.id.navHost) as? SearchFragment?
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, searchFragment ?: SearchFragment.newInstance()).commit();
    }
}

