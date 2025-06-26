package com.pemrograman_platform.karareserve.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.appbar.MaterialToolbar
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.api.ApiClient
import com.pemrograman_platform.karareserve.data.BookingDetailResponse
import com.pemrograman_platform.karareserve.data.BookingDetailResult
import com.pemrograman_platform.karareserve.data.PaymentUpdateRequest
import com.pemrograman_platform.karareserve.databinding.ActivityPaymentBinding
import com.pemrograman_platform.karareserve.utils.formatRupiah
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        val toolbar: MaterialToolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        // Get booking UUID from intent
        val bookingUuid = intent.getStringExtra("BOOKING_UUID")
        if (bookingUuid != null) {
            fetchBookingDetail(bookingUuid)
        } else {
            Toast.makeText(this, "Booking UUID not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchBookingDetail(bookingUuid: String) {
        ApiClient.retrofitService.getBookingDetail(bookingUuid)
            .enqueue(object : Callback<BookingDetailResponse> {
                override fun onResponse(
                    call: Call<BookingDetailResponse>,
                    response: Response<BookingDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.result
                        if (result != null) {
                            updateUI(result)
                        }
                    } else {
                        Toast.makeText(this@PaymentActivity, "Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BookingDetailResponse>, t: Throwable) {
                    Toast.makeText(this@PaymentActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun updateUI(result: BookingDetailResult) {
        val room = result.room
        val booking = result.booking
        val payment = result.payment

        // Load room image
        val radiusInDp = 12
        val scale = binding.imageRoom.context.resources.displayMetrics.density
        val radiusInPx = (radiusInDp * scale).toInt()

        Glide.with(this)
            .load("http://10.0.2.2:3000${room.imagePath}")
            .transform(CenterCrop(), RoundedCorners(radiusInPx))
            .into(binding.imageRoom)

        binding.roomTitle.text = room.roomType
        binding.textBookingStatus.text = booking.bookingStatus.capitalize(Locale.ROOT)
        binding.textBookingDate.text = "${booking.bookingDate} | ${booking.startTime} - ${booking.endTime}"

        if (payment != null) {
            if (payment.paymentStatus == "unpaid") {
                binding.cardUnpaid.visibility = View.VISIBLE
                binding.cardPaid.visibility = View.GONE

                binding.unpaidPaymentMethod.text = payment.paymentMethod.uppercase()
                binding.unpaidAmount.text = formatRupiah(payment.amount)

                if (payment.paymentMethod != "qris") {
                    binding.unpaidIcon.setImageResource(R.drawable.ic_barcode)
                    binding.unpaidInfo.visibility = View.VISIBLE
                } else {
                    binding.unpaidIcon.setImageResource(R.drawable.ic_qr)
                    binding.unpaidInfo.visibility = View.GONE
                }
            }

            if (payment.paymentStatus == "paid") {
                binding.footerContainer.visibility = View.GONE
                binding.cardUnpaid.visibility = View.GONE
                binding.cardPaid.visibility = View.VISIBLE

                binding.paidStatus.text = payment.paymentStatus.uppercase()
                binding.paidPaymentMethod.text = payment.paymentMethod.uppercase()
                binding.paidAmount.text = formatRupiah(payment.amount)
                binding.paidPaymentDate.text = payment.paymentDate ?: "-"
            }
        }

        if (booking.bookingStatus == "cancelled") {
            binding.labelDetail.visibility = View.GONE
            binding.footerContainer.visibility = View.GONE
            binding.cardCancelled.visibility = View.VISIBLE
        }

        binding.btnCancel.setOnClickListener {
            binding.progressContainer.visibility = View.VISIBLE

            cancelBooking(booking.bookingUuid)
        }

        binding.btnPayment.setOnClickListener {
            binding.progressContainer.visibility = View.VISIBLE

            updatePaymentStatus(booking.bookingUuid, payment.paymentMethod)
        }
    }

    private fun updatePaymentStatus(bookingUuid: String, paymentMethod: String) {
        val requestBody = PaymentUpdateRequest(
            payment_method = paymentMethod,
            payment_status = "paid"
        )

        ApiClient.retrofitService.updatePaymentStatus(bookingUuid, requestBody)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PaymentActivity, "Payment confirmed successfully", Toast.LENGTH_SHORT).show()

                        finish()
                    } else {
                        Toast.makeText(this@PaymentActivity, "Failed to update payment: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@PaymentActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun cancelBooking(bookingUuid: String) {
        ApiClient.retrofitService.cancelBooking(bookingUuid)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PaymentActivity, "Booking canceled successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@PaymentActivity, "Failed to cancel: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@PaymentActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
