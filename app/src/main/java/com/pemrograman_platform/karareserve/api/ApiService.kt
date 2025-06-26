package com.pemrograman_platform.karareserve.api

import com.pemrograman_platform.karareserve.data.BookingDetailResponse
import com.pemrograman_platform.karareserve.data.BookingRequest
import com.pemrograman_platform.karareserve.data.BookingResponse
import com.pemrograman_platform.karareserve.data.KaraokeRoomDetailResponse
import com.pemrograman_platform.karareserve.data.KaraokeRoomResponse
import com.pemrograman_platform.karareserve.data.PaymentUpdateRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomApiService {
    @GET("/room")
    suspend fun getRooms(): KaraokeRoomResponse

    @GET("/room/{uuid}")
    suspend fun getRoomByUuid(
        @Path("uuid") uuid: String
    ): KaraokeRoomDetailResponse

    @POST("booking/")
    fun postBooking(
        @Body request: BookingRequest
    ): Call<ResponseBody>

    @GET("/booking/{user}")
    fun getBookings(
        @Path("user") userUuid: String
    ): Call<BookingResponse>

    @GET("booking/detail/{booking_uuid}")
    fun getBookingDetail(
        @Path("booking_uuid") bookingUuid: String
    ): Call<BookingDetailResponse>

    @PUT("payment/{booking_uuid}")
    fun updatePaymentStatus(
        @Path("booking_uuid") bookingUuid: String,
        @Body updateRequest: PaymentUpdateRequest
    ): Call<Void>

    @DELETE("booking/{booking_uuid}")
    fun cancelBooking(
        @Path("booking_uuid") bookingUuid: String
    ): Call<Void>
}
