package com.roman.airtickets.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.roman.airtickets.domain.UseCase
import com.roman.airtickets.entity.*
import com.roman.airtickets.presentation.StateLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private var _stateLoadingSearchTickets = MutableStateFlow<StateLoading>(StateLoading.Loaded)
    val stateSearchTickets = _stateLoadingSearchTickets.asStateFlow()
    private var _stateLoadingAllTickets = MutableStateFlow<StateLoading>(StateLoading.Loaded)
    val stateAllTickets = _stateLoadingAllTickets.asStateFlow()
    private var _tickets: List<TicketRecommendations> = emptyList()
    val tickets get() = _tickets

    private var _routFrom = ""
    private var _routTo = ""
    val routFrom get() = _routFrom
    val routTo get() = _routTo
    val countPeople = 1
    private var _dateFrom = 0L
    val dateFrom get() = _dateFrom
    private var _dateTo = 0L
    val dateTo get() = _dateTo
    private val formatDateViewModel = SimpleDateFormat("d MMMM", Locale.getDefault())
    suspend fun getMusicFly(): MusicFlay {
        return useCase.getMusicFly()
    }

    init {

    }

    suspend fun refreshSearchFragment() {
        _stateLoadingSearchTickets.value = StateLoading.Loading
        setDateTo(0L)
        setDateFrom(0L)
        getRecommendationsTicket()
        _stateLoadingSearchTickets.value = StateLoading.Loaded
    }


    private suspend fun getRecommendationsTicket() {
        _tickets = useCase.getRecommendationsTicket().ticketsOffers
    }

    suspend fun getTicketInformFull(): Tickets {
        return useCase.getTicketInformFull()
    }

    fun setDateFrom(millSec: Long) {
        _dateFrom = millSec
    }

    fun setDateTo(millSec: Long) {
        _dateTo = millSec
    }

    fun getFromDayAndMouth(): String {

        return formatDateViewModel.format(_dateFrom).toString()
    }

    fun getToDayAndMouth(): String {

        return if (_dateTo != 0L) formatDateViewModel.format(_dateTo).toString()
        else ""
    }

    fun setRoutFrom(text: String) {
        _routFrom = text
    }

    fun replace() {
        val text = _routFrom
        _routFrom = _routTo
        _routTo = text
    }

    fun setRoutTo() {
        _routTo = ""
    }

    fun setRoutTo(text: String) {
        _routTo = text
    }
}