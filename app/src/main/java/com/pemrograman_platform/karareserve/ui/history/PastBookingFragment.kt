package com.pemrograman_platform.karareserve.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.adapter.BookingHistoryAdapter
import com.pemrograman_platform.karareserve.data.BookingHistory

class PastBookingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var roomAdapter: BookingHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewBooking)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val bookingList = listOf(
            BookingHistory(R.drawable.deluxe_room, "Deluxe Room", "27 Feb 2025 - 15:00"),
            BookingHistory(R.drawable.regular_room, "Regular Room", "14 Feb 2025 - 22:00")
        )

        roomAdapter = BookingHistoryAdapter(bookingList) { /* No action */ }
        recyclerView.adapter = roomAdapter

        return view
    }
}
