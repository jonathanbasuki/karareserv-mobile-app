package com.pemrograman_platform.karareserve.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemrograman_platform.karareserve.api.ApiClient
import com.pemrograman_platform.karareserve.data.KaraokeRoom
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {
    private val _rooms = MutableLiveData<List<KaraokeRoom>>()
    val rooms: LiveData<List<KaraokeRoom>> = _rooms

    init {
        fetchRooms()
    }

    private fun fetchRooms() {
        viewModelScope.launch {
            try {
                val response = ApiClient.retrofitService.getRooms()
                _rooms.value = response.rooms
            } catch (e: Exception) {
                Log.e("RoomViewModel", "Failed to fetch rooms: ${e.message}")
            }
        }
    }
}
