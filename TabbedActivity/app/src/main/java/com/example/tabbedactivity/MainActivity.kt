package com.example.tabbedactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val titlesArray = resources.getStringArray(R.array.movies_titles)
        val ratingArray = resources.getStringArray(R.array.movies_rating)
        val descriptionsArray = resources.getStringArray(R.array.movies_descriptions)
        val genreArray = resources.getStringArray(R.array.movies_genre)
        val countryArray = resources.getStringArray(R.array.movies_country)


        val adapter = ViewPagerAdapter(
            this, titlesArray, ratingArray,
            descriptionsArray, genreArray, countryArray
        )
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "${titlesArray[position]}"
        }.attach()
    }
}