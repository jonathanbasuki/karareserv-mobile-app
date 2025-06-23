package com.pemrograman_platform.karareserve.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.data.KaraokeRoom

class KaraokeRoomAdapter(
    private var rooms: List<KaraokeRoom>,
    private val onClick: (KaraokeRoom) -> Unit
) : RecyclerView.Adapter<KaraokeRoomAdapter.RoomViewHolder>() {

    fun updateData(newRooms: List<KaraokeRoom>) {
        rooms = newRooms
        notifyDataSetChanged()
    }

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageRoom)
        val type: TextView = itemView.findViewById(R.id.textRoomType)
        val capacity: TextView = itemView.findViewById(R.id.textCapacity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]

        val radiusInDp = 8
        val scale = holder.itemView.context.resources.displayMetrics.density
        val radiusInPx = (radiusInDp * scale).toInt()

        Glide.with(holder.itemView.context)
            .load(room.getFullImageUrl())
            .transform(CenterCrop(), RoundedCorners(radiusInPx))
            .into(holder.image)

        holder.type.text = room.roomType
        holder.capacity.text = "${room.roomCapacity} persons"

        holder.itemView.setOnClickListener { onClick(room) }
    }

    override fun getItemCount(): Int = rooms.size
}