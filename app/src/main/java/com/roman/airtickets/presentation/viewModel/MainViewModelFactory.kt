package com.roman.airtickets.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roman.airtickets.domain.UseCase
import javax.inject.Inject


class MainViewModelFactory @Inject constructor(
    private val useCase: UseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(useCase) as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name ")
    }

}