package com.pemrograman_platform.karareserve

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pemrograman_platform.karareserve.databinding.ActivityMainBinding
import com.pemrograman_platform.karareserve.ui.home.HomeFragment
import com.pemrograman_platform.karareserve.ui.history.HistoryFragment
import com.pemrograman_platform.karareserve.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }

        binding.bottomNavigation.setOnShowListener { item ->
            binding.bottomNavigation.clearCountDelayed(item.id, 200)

            when (item.id) {
                0 -> {
                    loadFragment(HomeFragment())
                    true
                }
                1 -> {
                    loadFragment(HistoryFragment())
                    true
                }
                2 -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}