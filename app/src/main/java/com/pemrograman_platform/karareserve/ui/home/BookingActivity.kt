package com.pemrograman_platform.karareserve.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.databinding.ActivityBookingBinding
import com.pemrograman_platform.karareserve.utils.formatRupiah

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: MaterialToolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val roomType = intent.getStringExtra("ROOM_TYPE")
        val roomPrice = intent.getStringExtra("ROOM_PRICE")
        val roomImage = intent.getStringExtra("ROOM_IMAGE")

        binding.roomName.text = roomType
        binding.roomPrice.text = "${roomPrice?.let { formatRupiah(it) }} / hour"

        Glide.with(this)
            .load(roomImage)
            .into(binding.roomImage)
    }
}