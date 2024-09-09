package com.example.composenews.ui.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composenews.data.PostsRepository
import com.example.composenews.data.Result
import com.example.composenews.models.ErrorMessage
import com.example.composenews.models.Post
import com.example.composenews.models.PostsFeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.log

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
        val postsFeed: PostsFeed,
        val selectedPost: Post,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiStates

}

private data class HomeViewModelState(
    val postsFeed: PostsFeed? = null,
    val selectedPostId: String? = null, // TODO back selectedPostId in a SavedStateHandle
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
){
    fun toUiState():HomeUiStates =
        if (postsFeed==null){
            HomeUiStates.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }else{
            HomeUiStates.HasPosts(
                postsFeed = postsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                selectedPost = postsFeed.allPosts.find {
                    it.id==selectedPostId
                }?:postsFeed.highlightedPost,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
}

class HomeViewModel(
    private val postsRepository: PostsRepository?,
    preSelectedPostId: String?
) : ViewModel(){

    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
            selectedPostId = preSelectedPostId,
            isArticleOpen = preSelectedPostId != null
        )
    )

    val uiState = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )


    init {
        refreshPost()

        // Observe for favorite changes in the repo layer
        viewModelScope.launch {
            postsRepository?.observeFavorites()?.collect { favorites ->
                viewModelState.update { it.copy(favorites = favorites) }
            }
        }
    }

    fun refreshPost(){
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = postsRepository?.getPostsFeed()
            viewModelState.update {
                when(result){
                    is Result.Success -> {
                        Log.d("{HOMEViewModel}", "refreshPost: ${result.data}")
                        it.copy(postsFeed = result.data, isLoading = false)

                    }
                    is Result.Error -> {
                        val errorMsg = it.errorMessages
                        HomeViewModelState(errorMessages = errorMsg, isLoading = false)
                        //it.copy(errorMessages = errorMsg, isLoading = false)
                    }
                    null -> TODO()
                }
            }
        }
    }



    companion object {
        fun provideFactory(
            postsRepository: PostsRepository,
            preSelectedPostId: String? = null
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(postsRepository, preSelectedPostId) as T
            }
        }
    }
}