package com.roman.airtickets.data

import com.roman.airtickets.entity.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MusicFlayDto(override val offers: List<OffersValueDto>) : MusicFlay

@JsonClass(generateAdapter = true)
class OffersValueDto(
    override val id: Int,
    override val title: String?,
    override val town: String?,
    override val price: PriceValueDto
) : OffersValue


@JsonClass(generateAdapter = true)
class PriceValueDto(override val value: Int) : PriceValue

@JsonClass(generateAdapter = true)
class TicketsRecommendationsListDto(
    @Json(name = "tickets_offers") override val ticketsOffers: List<TicketRecommendationsDto>
) : TicketsRecommendationsList

@JsonClass(generateAdapter = true)
class TicketRecommendationsDto(
    override val id: Int,
    override val title: String?,
    override val price: PriceValueDto,
    @Json(name = "time_range") override val timeRange: List<String>
) : TicketRecommendations

@JsonClass(generateAdapter = true)
class TicketsDto(
    override val tickets: List<TicketsInformFullDto>
) : Tickets


@JsonClass(generateAdapter = true)
class TicketsInformFullDto(
    override val id: Int,
    override val badge: String?,
    override val price: PriceValueDto,
    override val departure: DepartureArrivalDto,
    override val arrival: DepartureArrivalDto,
    @Json(name = "has_transfer") override val hasTransfer: Boolean,
) : TicketsInformFull

@JsonClass(generateAdapter = true)
class DepartureArrivalDto(
    override val town: String,
    override val date: String,
    override val airport: String,
) : DepartureArrival