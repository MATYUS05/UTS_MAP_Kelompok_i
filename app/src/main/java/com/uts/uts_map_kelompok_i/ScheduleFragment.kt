package com.uts.uts_map_kelompok_i

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.uts.uts_map_kelompok_i.adapter.SchedulePagerAdapter

class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        val tabLayout: TabLayout = view.findViewById(R.id.schedule_tab_layout)
        val viewPager: ViewPager2 = view.findViewById(R.id.schedule_view_pager)

        viewPager.adapter = SchedulePagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Jadwal Aktif"
                1 -> "Riwayat"
                else -> null
            }
        }.attach()

        return view
    }
}