package com.uts.uts_map_kelompok_i.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uts.uts_map_kelompok_i.ActiveScheduleFragment
import com.uts.uts_map_kelompok_i.HistoryScheduleFragment

class SchedulePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveScheduleFragment()
            1 -> HistoryScheduleFragment()
            else -> throw IllegalStateException("Posisi tidak valid: $position")
        }
    }
}