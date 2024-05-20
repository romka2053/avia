package com.roman.airtickets.presentation

sealed class StateLoading {
    object Loading : StateLoading()
    object Loaded : StateLoading()
    class Error(val error: String) : StateLoading()
}