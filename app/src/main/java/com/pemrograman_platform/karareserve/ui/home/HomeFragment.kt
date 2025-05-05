package com.pemrograman_platform.karareserve.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.adapter.KaraokeRoomAdapter
import com.pemrograman_platform.karareserve.data.KaraokeRoom
import com.pemrograman_platform.karareserve.utils.GridSpacingItemDecoration

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var roomAdapter: KaraokeRoomAdapter
    private lateinit var roomList: List<KaraokeRoom>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewRooms)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val spanCount = 2
        val spacingInDp = 16

        val scale = resources.displayMetrics.density
        val spacingInPx = (spacingInDp * scale + 0.5f).toInt()

        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(spanCount, spacingInPx, false)
        )

        roomList = listOf(
            KaraokeRoom(R.drawable.vip_room, "VIP Room", "10 persons", "Rp480.000"),
            KaraokeRoom(R.drawable.deluxe_room, "Deluxe Room", "8 persons", "Rp400.000"),
            KaraokeRoom(R.drawable.classic_room, "Classic Room", "6 persons", "Rp320.000"),
            KaraokeRoom(R.drawable.family_room, "Family Room", "12 persons", "Rp460.000"),
            KaraokeRoom(R.drawable.regular_room, "Regular Room", "4 persons", "Rp250.000")
        )

        roomAdapter = KaraokeRoomAdapter(roomList) { selectedRoom ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("ROOM_IMAGE", selectedRoom.imageResId)
                putExtra("ROOM_TYPE", selectedRoom.roomType)
                putExtra("ROOM_CAPACITY", selectedRoom.capacity)
                putExtra("ROOM_PRICE", selectedRoom.roomPrice)
            }

            startActivity(intent)
        }

        recyclerView.adapter = roomAdapter
    }
}
