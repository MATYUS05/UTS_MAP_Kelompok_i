package com.uts.uts_map_kelompok_i.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uts.uts_map_kelompok_i.AllPostsFragment
import com.uts.uts_map_kelompok_i.YourPostsFragment

class PartnerPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllPostsFragment()
            1 -> YourPostsFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}