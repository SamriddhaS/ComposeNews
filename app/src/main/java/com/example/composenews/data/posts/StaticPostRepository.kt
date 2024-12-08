package com.example.composenews.data.posts

import com.example.composenews.data.Result
import com.example.composenews.models.Post
import com.example.composenews.models.PostsFeed
import com.example.composenews.utils.addOrRemove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class StaticPostRepository: PostsRepository {

    /*
    * For time being we are storing data in memory because we don't have db or api yet
    * to store them.
    * */
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    private val postsFeed = MutableStateFlow<PostsFeed?>(null)
    override suspend fun getPostById(postId: String?): Result<Post> {
        return withContext(Dispatchers.IO){
            val post = posts.allPosts.find {
                it.id == postId
            }
            post?.run {
                Result.Success(this)
            }?:run{
                Result.Error(IllegalArgumentException("Post not found"))
            }
        }
    }

    override suspend fun getPostsFeed(): Result<PostsFeed> {
        return withContext(Dispatchers.IO){
            delay(2000)
            if (shouldRandomlyFail()){
                Result.Error(IllegalArgumentException("Failed to fetch posts"))
            }else{
                postsFeed.update { posts }
                Result.Success(posts)
            }
        }
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites

    override fun observePostsFeed(): Flow<PostsFeed?> = postsFeed

    override suspend fun toggleFavorite(postId: String) {
        favorites.update {
            it.addOrRemove(postId)
        }
    }

    // used to drive "random" failure in a predictable pattern, making the first request always
    // succeed
    private var requestCount = 0
    /**
     * Randomly fail some loads to simulate a real network.
     *
     * This will fail deterministically every 5 requests
     */
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}