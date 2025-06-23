package com.pemrograman_platform.karareserve.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.pemrograman_platform.karareserve.MainActivity
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.api.ApiClient
import com.pemrograman_platform.karareserve.data.BookingRequest
import com.pemrograman_platform.karareserve.databinding.ActivityBookingBinding
import com.pemrograman_platform.karareserve.utils.formatRupiah
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var roomUuid: String

    private var selectedTime: String? = null
    private var selectedPaymentMethod: String? = null
    private var durationHours = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Get data from intent
        roomUuid = intent.getStringExtra("ROOM_UUID") ?: return showToast("Room UUID missing")
        val roomType = intent.getStringExtra("ROOM_TYPE")
        val roomPrice = intent.getStringExtra("ROOM_PRICE")
        val roomImage = intent.getStringExtra("ROOM_IMAGE")

        // Set room info
        binding.roomName.text = roomType
        binding.roomPrice.text = "${roomPrice?.let { formatRupiah(it) }} / hour"
        Glide.with(this).load(roomImage).into(binding.roomImage)

        setupDatePicker()
        setupDurationButtons()
        setupDynamicTimeChips()
        setupDynamicPaymentChips()

        // Handle book now
        binding.btnBookNow.setOnClickListener {
            binding.progressContainer.visibility = View.VISIBLE
            postBooking()
        }
    }

    private fun setupDatePicker() {
        binding.inputDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    val date = String.format("%04d-%02d-%02d", y, m + 1, d)
                    binding.inputDate.setText(date)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupDurationButtons() {
        updateDurationText()
        binding.btnPlus.setOnClickListener {
            durationHours++
            updateDurationText()
        }
        binding.btnMinus.setOnClickListener {
            if (durationHours > 1) {
                durationHours--
                updateDurationText()
            } else {
                showToast("Minimum duration is 1 hour")
            }
        }
    }

    private fun updateDurationText() {
        binding.textDuration.text = durationHours.toString()
    }

    private fun setupDynamicTimeChips() {
        val times = listOf("08:00", "10:00", "13:00", "15:00", "18:00", "20:00", "22:00")
        binding.chipGroupTime.removeAllViews()

        for (time in times) {
            val chip = Chip(this).apply {
                text = time
                isCheckable = true
                setChipBackgroundColorResource(R.color.background)
                chipStrokeColor = ContextCompat.getColorStateList(context, R.color.primary)
                chipStrokeWidth = 2f
                setTextColor(ContextCompat.getColor(context, R.color.onBackground))
            }

            chip.setOnClickListener {
                selectedTime = time
                highlightSelectedChip(binding.chipGroupTime, chip)
            }

            binding.chipGroupTime.addView(chip)
        }
    }

    private fun setupDynamicPaymentChips() {
        val methods = listOf("QRIS", "Card", "Cash")
        binding.chipGroupPayment.removeAllViews()

        for (method in methods) {
            val chip = Chip(this).apply {
                text = method
                isCheckable = true
                setChipBackgroundColorResource(R.color.background)
                chipStrokeColor = ContextCompat.getColorStateList(context, R.color.primary)
                chipStrokeWidth = 2f
                setTextColor(ContextCompat.getColor(context, R.color.onBackground))
            }

            chip.setOnClickListener {
                selectedPaymentMethod = method
                highlightSelectedChip(binding.chipGroupPayment, chip)
            }

            binding.chipGroupPayment.addView(chip)
        }
    }

    private fun highlightSelectedChip(chipGroup: ChipGroup, selectedChip: Chip) {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setChipBackgroundColorResource(R.color.background)
            chip.setTextColor(ContextCompat.getColor(this, R.color.onBackground))
        }
        selectedChip.setChipBackgroundColorResource(R.color.primary)
        selectedChip.setTextColor(ContextCompat.getColor(this, R.color.background))
    }

    private fun postBooking() {
        val user = FirebaseAuth.getInstance().currentUser ?: return showToast("User not logged in")
        val userUuid = user.uid
        val bookingDate = binding.inputDate.text.toString().takeIf { it.isNotBlank() }
            ?: return showToast("Please select a booking date")
        val startTime = selectedTime ?: return showToast("Please select booking time")
        val payment = selectedPaymentMethod ?: return showToast("Please select payment method")

        // Calculate end time
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val start = sdf.parse(startTime) ?: return showToast("Invalid start time")
        val cal = Calendar.getInstance().apply {
            time = start
            add(Calendar.HOUR_OF_DAY, durationHours)
        }
        val endTime = sdf.format(cal.time)

        val bookingRequest = BookingRequest(
            user_uuid = userUuid,
            room_uuid = roomUuid,
            booking_date = bookingDate,
            start_time = startTime,
            end_time = endTime,
            payment_method = payment.lowercase()
        )

        ApiClient.retrofitService.postBooking(bookingRequest).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    showToast("Booking successful")

                    val intent = Intent(this@BookingActivity, MainActivity::class.java)
                    intent.putExtra("NAVIGATE_TO", "history")
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

                    startActivity(intent)
                    finish()
                } else {
                    showToast("Failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showToast("Network error: ${t.localizedMessage}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
