package com.pemrograman_platform.karareserve.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.data.BookingHistory

class BookingHistoryAdapter(
    private val bookingList: List<BookingHistory>,
    private val onItemClick: (BookingHistory) -> Unit
) : RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRoom: ImageView = itemView.findViewById(R.id.imageRoom)
        val textRoomType: TextView = itemView.findViewById(R.id.textRoomType)
        val textBookingInfo: TextView = itemView.findViewById(R.id.textCapacity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]

        holder.imageRoom.setImageResource(booking.imageResId)
        holder.textRoomType.text = booking.roomType
        holder.textBookingInfo.text = booking.bookingDate
        holder.itemView.setOnClickListener {
            onItemClick(booking)
        }
    }

    override fun getItemCount(): Int = bookingList.size
}
