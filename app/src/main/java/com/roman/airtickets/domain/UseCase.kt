package com.roman.airtickets.domain

import com.roman.airtickets.data.TicketsRepository
import com.roman.airtickets.entity.MusicFlay
import com.roman.airtickets.entity.Tickets
import com.roman.airtickets.entity.TicketsRecommendationsList
import javax.inject.Inject

class UseCase @Inject constructor(
    private val ticketsRepository: TicketsRepository
) {

    suspend fun getMusicFly(): MusicFlay {
        return ticketsRepository.getMusicFly()
    }

    suspend fun getRecommendationsTicket(): TicketsRecommendationsList {
        return ticketsRepository.getRecommendationsTicket()
    }

    suspend fun getTicketInformFull(): Tickets {
        return ticketsRepository.getTicketInformFull()
    }
}