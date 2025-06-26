package com.pemrograman_platform.karareserve.ui.history

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.pemrograman_platform.karareserve.adapter.BookingHistoryAdapter
import com.pemrograman_platform.karareserve.api.ApiClient
import com.pemrograman_platform.karareserve.data.BookingHistory
import com.pemrograman_platform.karareserve.data.BookingResponse
import com.pemrograman_platform.karareserve.databinding.FragmentHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var roomAdapter: BookingHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.recyclerViewBooking.layoutManager = LinearLayoutManager(context)

        roomAdapter = BookingHistoryAdapter(emptyList()) { booking ->
            val intent = Intent(requireContext(), PaymentActivity::class.java).apply {
                putExtra("BOOKING_UUID", booking.bookingUuid)
            }

            startActivity(intent)
        }

        binding.recyclerViewBooking.adapter = roomAdapter

        val marginBottomInPx = (16 * resources.displayMetrics.density).toInt()
        binding.recyclerViewBooking.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val itemCount = parent.adapter?.itemCount ?: 0
                if (position < itemCount - 1) {
                    outRect.set(0, 0, 0, marginBottomInPx)
                } else {
                    outRect.set(0, 0, 0, 0)
                }
            }
        })


        fetchBookingHistory()

        return binding.root
    }

    private fun fetchBookingHistory() {
        val user = FirebaseAuth.getInstance().currentUser
        val userUuid = user?.uid ?: return showToast("User not logged in")

        ApiClient.retrofitService.getBookings(userUuid)
            .enqueue(object : Callback<BookingResponse> {
                override fun onResponse(
                    call: Call<BookingResponse>,
                    response: Response<BookingResponse>
                ) {
                    if (response.isSuccessful) {
                        val bookings = response.body()?.result ?: emptyList()
                        updateBookingList(bookings)
                    } else {
                        showToast("Failed to fetch data: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                    showToast("Network error: ${t.message}")
                }
            })
    }

    private fun updateBookingList(bookings: List<BookingHistory>) {
        if (bookings.isEmpty()) {
            binding.cardEmpty.visibility = View.VISIBLE
            binding.recyclerViewBooking.visibility = View.GONE
        } else {
            binding.cardEmpty.visibility = View.GONE
            binding.recyclerViewBooking.visibility = View.VISIBLE
            roomAdapter.updateData(bookings)
        }
    }


    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}

