package com.example.composenews.ui.interestsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composenews.data.interests.InterestRepository

class InterestViewModel(
    private val interestRepository: InterestRepository
):ViewModel() {


    companion object {
        fun provideFactory(
            interestsRepository: InterestRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return InterestViewModel(interestsRepository) as T
            }
        }
    }
}