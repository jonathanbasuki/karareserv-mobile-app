package com.pemrograman_platform.karareserve.data

import com.google.gson.annotations.SerializedName

data class KaraokeRoomResponse(
    @SerializedName("message") val message: String,
    @SerializedName("rooms") val rooms: List<KaraokeRoom>
)

data class KaraokeRoom(
    @SerializedName("room_uuid") val uuid: String,
    @SerializedName("room_type") val roomType: String,
    @SerializedName("room_capacity") val roomCapacity: Int,
    @SerializedName("image") val imagePath: String
) {
    fun getFullImageUrl(): String {
        return "http://10.0.2.2:3000$imagePath"
    }
}