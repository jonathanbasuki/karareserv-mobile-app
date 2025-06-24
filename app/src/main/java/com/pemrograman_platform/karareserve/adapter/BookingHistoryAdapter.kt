package com.pemrograman_platform.karareserve.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.data.BookingHistory
import java.text.SimpleDateFormat
import java.util.Locale

class BookingHistoryAdapter(
    private var bookingList: List<BookingHistory>,
    private val onItemClick: (BookingHistory) -> Unit
) : RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRoom: ImageView = itemView.findViewById(R.id.imageRoom)
        val textRoomType: TextView = itemView.findViewById(R.id.textRoomType)
        val textBookingInfo: TextView = itemView.findViewById(R.id.textCapacity)
        val iconInfo: ImageView = itemView.findViewById(R.id.iconInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]
        val context = holder.itemView.context

        holder.textRoomType.text = booking.room.roomType
        holder.iconInfo.setImageResource(R.drawable.ic_calendar)
        holder.textBookingInfo.text =
            "${booking.bookingDate} - ${booking.startTime.substring(0, 5)}"

        // Load image from server
        val imageUrl = "http://10.0.2.2:3000${booking.room.roomImage}"
        Glide.with(context)
            .load(imageUrl)
            .into(holder.imageRoom)

        holder.itemView.setOnClickListener {
            onItemClick(booking)
        }
    }

    override fun getItemCount(): Int = bookingList.size

    fun updateData(newData: List<BookingHistory>) {
        bookingList = newData
        notifyDataSetChanged()
    }
}
