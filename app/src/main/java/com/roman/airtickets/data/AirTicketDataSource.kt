package com.roman.airtickets.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

private const val BASE_URL = "https://run.mocky.io/"

class AirTicketDataSource @Inject constructor() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    val musicFlay: GetMusicFly = retrofit.create(GetMusicFly::class.java)
    val recommendationsTicket: GetRecommendationsTicket =
        retrofit.create(GetRecommendationsTicket::class.java)
    val ticketInformFull: GetFullTicketsInform = retrofit.create(GetFullTicketsInform::class.java)

    interface GetMusicFly {
        @GET("v3/214a1713-bac0-4853-907c-a1dfc3cd05fd")
        suspend fun getMusicFly(): MusicFlayDto
    }

    interface GetRecommendationsTicket {
        @GET("v3/7e55bf02-89ff-4847-9eb7-7d83ef884017")
        suspend fun getRecommendationsTicket(): TicketsRecommendationsListDto
    }

    interface GetFullTicketsInform {
        @GET("v3/670c3d56-7f03-4237-9e34-d437a9e56ebf")
        suspend fun getFullTicketsInform(): TicketsDto
    }
}