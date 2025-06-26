package com.pemrograman_platform.karareserve.data

import com.google.gson.annotations.SerializedName

// Model for create booking
data class BookingRequest(
    val user_uuid: String,
    val room_uuid: String,
    val booking_date: String,
    val start_time: String,
    val end_time: String,
    val payment_method: String
)

// Model for get all bookings
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

// Model for get detail booking
data class BookingDetailResponse(
    val message: String,
    val result: BookingDetailResult
)

data class BookingDetailResult(
    val booking: BookingDetail,
    val room: KaraokeRoom,
    val payment: PaymentDetail
)

data class BookingDetail(
    @SerializedName("booking_uuid") val bookingUuid: String,
    @SerializedName("booking_date") val bookingDate: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("total_price") val totalPrice: String,
    @SerializedName("booking_status") val bookingStatus: String
)

data class PaymentDetail(
    @SerializedName("payment_uuid") val paymentUuid: String,
    @SerializedName("payment_date") val paymentDate: String?,
    @SerializedName("amount") val amount: String,
    @SerializedName("payment_method") val paymentMethod: String,
    @SerializedName("payment_status") val paymentStatus: String,
)

data class PaymentUpdateRequest(
    val payment_method: String,
    val payment_status: String
)
