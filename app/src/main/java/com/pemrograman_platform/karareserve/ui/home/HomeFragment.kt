package com.pemrograman_platform.karareserve.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.adapter.KaraokeRoomAdapter
import com.pemrograman_platform.karareserve.data.KaraokeRoom
import com.pemrograman_platform.karareserve.utils.GridSpacingItemDecoration
import com.pemrograman_platform.karareserve.viewmodel.RoomViewModel

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var roomAdapter: KaraokeRoomAdapter
    private val viewModel: RoomViewModel by viewModels()

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

        val spacingInPx = (16 * resources.displayMetrics.density).toInt()
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPx, false))

        roomAdapter = KaraokeRoomAdapter(emptyList()) { selectedRoom ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("ROOM_UUID", selectedRoom.uuid)
            }
            startActivity(intent)
        }

        recyclerView.adapter = roomAdapter

        viewModel.rooms.observe(viewLifecycleOwner) { rooms ->
            roomAdapter.updateData(rooms)
        }
    }
}