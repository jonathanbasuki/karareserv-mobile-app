package com.pemrograman_platform.karareserve.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.adapter.ImageSliderAdapter
import com.pemrograman_platform.karareserve.databinding.ActivityDetailBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private lateinit var imageSlider: ViewPager2
    private lateinit var imageAdapter: ImageSliderAdapter
    private lateinit var dotsIndicator: DotsIndicator

    private var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: MaterialToolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val roomType = intent.getStringExtra("ROOM_TYPE")
        val roomCapacity = intent.getStringExtra("ROOM_CAPACITY")
        val roomPrice = intent.getStringExtra("ROOM_PRICE")

        binding.roomTitle.text = roomType
        binding.roomCapacity.text = roomCapacity
        binding.roomPrice.text = "${roomPrice} / hour"

        // Image slider setup
        imageSlider = binding.imageSlider
        dotsIndicator = binding.dotsIndicator

        // Setup image slider with dummy data
        imageAdapter = ImageSliderAdapter(
            listOf(
                R.drawable.vip_room,
                R.drawable.deluxe_room,
                R.drawable.classic_room,
                R.drawable.family_room,
                R.drawable.regular_room,
            )
        )
        imageSlider.adapter = imageAdapter

        // Set DotsIndicator to synchronize with ViewPager2
        dotsIndicator.setViewPager2(imageSlider)

        binding.btnReserve.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java).apply {
                putExtra("ROOM_TYPE", roomType)
                putExtra("ROOM_PRICE", roomPrice)
            }

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_toolbar, menu)

        val menuItem = menu?.findItem(R.id.action_favorite)
        val actionView = menuItem?.actionView
        val iconView = actionView?.findViewById<ImageView>(R.id.favorite_icon)

        updateFavoriteIcon(iconView)

        iconView?.setOnClickListener {
            isFavorited = !isFavorited

            updateFavoriteIcon(iconView)
        }

        return true
    }

    private fun updateFavoriteIcon(iconView: ImageView?) {
        val iconRes = if (isFavorited) R.drawable.ic_favorite_fill else R.drawable.ic_favorite

        iconView?.setImageResource(iconRes)
    }

}
