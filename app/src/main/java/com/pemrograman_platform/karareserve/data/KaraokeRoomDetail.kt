package com.pemrograman_platform.karareserve.data

import com.google.gson.annotations.SerializedName

data class KaraokeRoomDetailResponse(
    @SerializedName("message") val message: String,
    @SerializedName("details") val room: KaraokeRoomDetail
)

data class KaraokeRoomDetail(
    @SerializedName("room_uuid") val roomUuid: String,
    @SerializedName("room_number") val roomNumber: String,
    @SerializedName("room_type") val roomType: String,
    @SerializedName("room_capacity") val roomCapacity: Int,
    @SerializedName("room_description") val roomDescription: String,
    @SerializedName("room_status") val roomStatus: String,
    @SerializedName("hourly_rate") val hourlyRate: String,
    @SerializedName("images") val images: List<String>
) {
    fun getFullImageUrls(): List<String> {
        return images.map { "http://10.0.2.2:3000$it" }
    }
}