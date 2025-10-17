package com.uts.uts_map_kelompok_i

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_schedule -> {
                replaceFragment(ScheduleFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_partner -> {
                replaceFragment(PartnerFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_forum -> {
                replaceFragment(ForumFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stats -> {
                replaceFragment(StatisticFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                replaceFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            replaceFragment(ScheduleFragment())
            navView.selectedItemId = R.id.navigation_schedule
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun navigateToCreatePost() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CreatePostBottomSheetFragment())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToEditPost(partnerId: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, EditPostBottomSheetFragment.newInstance(partnerId))
            .addToBackStack(null)
            .commit()
    }
}