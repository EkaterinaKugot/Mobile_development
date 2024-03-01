package com.example.tabbedactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val titlesArray: Array<String>,
    private val ratingArray: Array<String>,
    private val descriptionsArray: Array<String>,
    private val genreArray: Array<String>,
    private val countryArray: Array<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return titlesArray.size
    }

    override fun createFragment(position: Int): Fragment {
        return MovieFragment.newInstance(
            titlesArray[position], ratingArray[position],
            descriptionsArray[position], genreArray[position], countryArray[position]
        )
    }
}