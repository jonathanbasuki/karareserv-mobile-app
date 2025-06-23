package com.pemrograman_platform.karareserve.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.adapter.ImageSliderAdapter
import com.pemrograman_platform.karareserve.api.ApiClient
import com.pemrograman_platform.karareserve.databinding.ActivityDetailBinding
import com.pemrograman_platform.karareserve.utils.formatRupiah
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.coroutines.launch

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
        toolbar.setNavigationOnClickListener { finish() }

        imageSlider = binding.imageSlider
        dotsIndicator = binding.dotsIndicator

        val roomUuid = intent.getStringExtra("ROOM_UUID") ?: return

        lifecycleScope.launch {
            try {
                val response = ApiClient.retrofitService.getRoomByUuid(roomUuid)
                val room = response.room

                binding.roomTitle.text = room.roomType
                binding.roomCapacity.text = "${room.roomCapacity} persons"
                binding.roomPrice.text = "${formatRupiah(room.hourlyRate)} / hour"
                binding.roomDescription.text = room.roomDescription

                imageAdapter = ImageSliderAdapter(room.getFullImageUrls())
                imageSlider.adapter = imageAdapter
                dotsIndicator.setViewPager2(imageSlider)

                binding.btnReserve.setOnClickListener {
                    val imageUrls = room.getFullImageUrls()
                    val firstImage = imageUrls.firstOrNull() ?: ""

                    val intent = Intent(this@DetailActivity, BookingActivity::class.java).apply {
                        putExtra("ROOM_UUID", room.roomUuid)
                        putExtra("ROOM_TYPE", room.roomType)
                        putExtra("ROOM_PRICE", room.hourlyRate)
                        putExtra("ROOM_IMAGE", firstImage)
                    }

                    startActivity(intent)
                }
            } catch (e: Exception) {
                Log.e("DetailActivity", "Failed to fetch room details: ${e.message}")
                Toast.makeText(this@DetailActivity, "Failed to load room details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_toolbar, menu)
        val menuItem = menu?.findItem(R.id.action_favorite)
        val iconView = menuItem?.actionView?.findViewById<ImageView>(R.id.favorite_icon)
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
