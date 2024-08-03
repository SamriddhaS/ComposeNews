package com.example.composenews.ui.homeScreen

import com.example.composenews.models.ErrorMessage

sealed interface HomeUiStates {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String


    /**
     * There are no posts to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiStates


    data class HasPosts(
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiStates




}