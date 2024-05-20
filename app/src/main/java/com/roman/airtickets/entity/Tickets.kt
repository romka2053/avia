package com.roman.airtickets.entity


interface MusicFlay {
    val offers: List<OffersValue>
}

interface TicketsRecommendationsList {
    val ticketsOffers: List<TicketRecommendations>
}

interface TicketRecommendations : Ticked {
    override val id: Int
    override val title: String?
    override val price: PriceValue
    val timeRange: List<String>
}

interface Ticked {
    val id: Int
    val title: String?
    val price: PriceValue
}

interface OffersValue : Ticked {
    override val id: Int
    override val title: String?
    val town: String?
    override val price: PriceValue
}

interface PriceValue {

    val value: Int
    fun getValue(): String {
        return String.format("%,d", value).plus(" ₽")
    }
}

interface Tickets {
    val tickets: List<TicketsInformFull>
}

interface TicketsInformFull {
    val id: Int
    val badge: String?
    val price: PriceValue
    val departure: DepartureArrival
    val arrival: DepartureArrival
    val hasTransfer: Boolean

}

interface DepartureArrival {
    val town: String
    val date: String
    val airport: String
}


