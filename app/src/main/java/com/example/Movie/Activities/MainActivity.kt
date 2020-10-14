package com.example.Movie.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.Movie.MovAdapter
import com.example.Movie.cache.MoviesDbHelper
import com.example.Movie.R
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {


    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
     lateinit var moviesDbHelper : MoviesDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesDbHelper = MoviesDbHelper(this)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Top-Rated"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Upcoming"))

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL


        val adapter = MovAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount,
            moviesDbHelper
        )
        viewPager!!.adapter = adapter
        viewPager!!.setOffscreenPageLimit(MovAdapter.NUM_PAGES)

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


}