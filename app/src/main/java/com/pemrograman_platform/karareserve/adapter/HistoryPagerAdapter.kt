package com.pemrograman_platform.karareserve.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pemrograman_platform.karareserve.ui.history.ActiveBookingFragment
import com.pemrograman_platform.karareserve.ui.history.PastBookingFragment

class HistoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveBookingFragment()
            else -> PastBookingFragment()
        }
    }
}
