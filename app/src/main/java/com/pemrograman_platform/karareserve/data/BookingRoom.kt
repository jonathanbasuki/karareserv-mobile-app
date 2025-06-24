package com.pemrograman_platform.karareserve.data

import com.google.gson.annotations.SerializedName

data class BookingRequest(
    val user_uuid: String,
    val room_uuid: String,
    val booking_date: String,
    val start_time: String,
    val end_time: String,
    val payment_method: String
)

data class BookingResponse(
    val message: String,
    val result: List<BookingHistory>
)

data class BookingHistory(
    @SerializedName("booking_uuid") val bookingUuid: String,
    @SerializedName("room_uuid") val roomUuid: String,
    @SerializedName("booking_date") val bookingDate: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    val room: BookingRoom
)

data class BookingRoom(
    @SerializedName("room_uuid") val roomUuid: String,
    @SerializedName("room_number") val roomNumber: String,
    @SerializedName("room_type") val roomType: String,
    @SerializedName("room_capacity") val roomCapacity: Int,
    @SerializedName("room_image") val roomImage: String
)
