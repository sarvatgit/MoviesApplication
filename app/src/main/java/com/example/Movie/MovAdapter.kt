package com.example.Movie

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MovAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int,var moviesDbHelper: MoviesDbHelper) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // this is for fragment tabs
    companion object {
        const val NUM_PAGES = 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return TopFragment(moviesDbHelper)
            }
            1 -> {
                return UpcomingFragment(moviesDbHelper)
            }

            else -> return TopFragment(moviesDbHelper)
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}