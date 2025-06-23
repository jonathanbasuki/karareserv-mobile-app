package com.pemrograman_platform.karareserve.data

data class BookingRequest(
    val user_uuid: String,
    val room_uuid: String,
    val booking_date: String,
    val start_time: String,
    val end_time: String,
    val payment_method: String
)