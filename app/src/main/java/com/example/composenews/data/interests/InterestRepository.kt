package com.example.composenews.data.interests

import kotlinx.coroutines.flow.Flow

data class InterestSection(val title: String, val interests: List<String>)

data class TopicSelection(val section: String, val topic: String)

interface InterestRepository{

    /**
    * Get list of available topics that we want to show
    * */
    suspend fun getTopic(): Result<List<InterestSection>>


    /**
    * Get list of people that we will show in the ui.
    * */
    suspend fun getPeopleList(): Result<List<String>>

    /**
     * Get list of Publications that we will show in the ui.
     * */
    suspend fun getPublicationsList(): Result<List<String>>

    /**
     * Toggle between a topic being selected and unselected.
     *
     */
    suspend fun toggleTopicSelection(topic: TopicSelection)

    /**
    * Toggle between person - selected and not selected.
    * */
    suspend fun togglePersonSelection(person:String)

    /**
     * Toggle between publication - selected and not selected.
     * */
    suspend fun togglePublicationSelection(publication:String)

    /**
     * Currently selected topics
     */
    fun observeTopicsSelected(): Flow<Set<TopicSelection>>

    /**
     * Currently selected people
     */
    fun observePeopleSelected(): Flow<Set<String>>

    /**
     * Currently selected publications
     */
    fun observePublicationSelected(): Flow<Set<String>>


}