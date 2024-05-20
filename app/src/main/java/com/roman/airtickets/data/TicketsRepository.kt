package com.roman.airtickets.data

import com.roman.airtickets.entity.MusicFlay
import com.roman.airtickets.entity.Tickets
import com.roman.airtickets.entity.TicketsRecommendationsList
import javax.inject.Inject

class TicketsRepository @Inject constructor(
    private val ticketsDataSource: AirTicketDataSource
) {


    suspend fun getMusicFly(): MusicFlay {
        return ticketsDataSource.musicFlay.getMusicFly()
    }

    suspend fun getRecommendationsTicket(): TicketsRecommendationsList {
        return ticketsDataSource.recommendationsTicket.getRecommendationsTicket()
    }

    suspend fun getTicketInformFull(): Tickets {
        return ticketsDataSource.ticketInformFull.getFullTicketsInform()
    }
}