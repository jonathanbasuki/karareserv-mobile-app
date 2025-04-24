package com.pemrograman_platform.karareserve.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.data.KaraokeRoom

class KaraokeRoomAdapter(
    private val roomList: List<KaraokeRoom>,
    private val onItemClick: (KaraokeRoom) -> Unit
) : RecyclerView.Adapter<KaraokeRoomAdapter.KaraokeRoomViewHolder>() {

    inner class KaraokeRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRoom: ImageView = itemView.findViewById(R.id.imageRoom)
        val textRoomType: TextView = itemView.findViewById(R.id.textRoomType)
        val textCapacity: TextView = itemView.findViewById(R.id.textCapacity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaraokeRoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return KaraokeRoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: KaraokeRoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.imageRoom.setImageResource(room.imageResId)
        holder.textRoomType.text = room.roomType
        holder.textCapacity.text = room.capacity

        holder.itemView.setOnClickListener {
            onItemClick(room)
        }
    }


    override fun getItemCount(): Int = roomList.size
}
