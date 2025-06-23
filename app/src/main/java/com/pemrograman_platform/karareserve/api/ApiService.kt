package com.pemrograman_platform.karareserve.api

import com.pemrograman_platform.karareserve.data.BookingRequest
import com.pemrograman_platform.karareserve.data.KaraokeRoomDetailResponse
import com.pemrograman_platform.karareserve.data.KaraokeRoomResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomApiService {
    @GET("/room")
    suspend fun getRooms(): KaraokeRoomResponse

    @GET("/room/{uuid}")
    suspend fun getRoomByUuid(@Path("uuid") uuid: String): KaraokeRoomDetailResponse

    @POST("booking/")
    fun postBooking(@Body request: BookingRequest): Call<ResponseBody>
}
