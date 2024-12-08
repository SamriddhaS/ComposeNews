package com.example.composenews.data.interests

import com.example.composenews.utils.addOrRemove
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class StaticInterestRepository:InterestRepository {
    override suspend fun getTopic(): Result<List<InterestSection>> {
        return Result.success(topics)
    }

    override suspend fun getPeopleList(): Result<List<String>> {
        return Result.success(people)
    }

    override suspend fun getPublicationsList(): Result<List<String>> {
        return Result.success(publications)
    }

    override suspend fun toggleTopicSelection(topic: TopicSelection) {
        selectedTopics.update {
            it.addOrRemove(topic)
        }
    }

    override suspend fun togglePersonSelection(person: String) {
        selectedPeople.update {
            it.addOrRemove(person)
        }
    }

    override suspend fun togglePublicationSelection(publication: String) {
        selectedPublications.update {
            it.addOrRemove(publication)
        }
    }

    override fun observeTopicsSelected(): Flow<Set<TopicSelection>> = selectedTopics

    override fun observePeopleSelected(): Flow<Set<String>> = selectedPeople

    override fun observePublicationSelected(): Flow<Set<String>> = selectedPublications

    private val topics by lazy {
        listOf(
            InterestSection("Android", listOf("Jetpack Compose", "Kotlin", "Jetpack")),
            InterestSection(
                "Programming",
                listOf("Kotlin", "Declarative UIs", "Java", "Unidirectional Data Flow", "C++")
            ),
            InterestSection("Technology", listOf("Pixel", "Google"))
        )
    }

    private val people by lazy {
        listOf(
            "Kobalt Toral",
            "K'Kola Uvarek",
            "Kris Vriloc",
            "Grala Valdyr",
            "Kruel Valaxar",
            "L'Elij Venonn",
            "Kraag Solazarn",
            "Tava Targesh",
            "Kemarrin Muuda"
        )
    }

    private val publications by lazy {
        listOf(
            "Kotlin Vibe",
            "Compose Mix",
            "Compose Breakdown",
            "Android Pursue",
            "Kotlin Watchman",
            "Jetpack Ark",
            "Composeshack",
            "Jetpack Point",
            "Compose Tribune"
        )
    }

    /*
    * These are our variable that will store the selection.
    * We are not using any api or db to store the selection.
    * */
    private val selectedTopics = MutableStateFlow(setOf<TopicSelection>())
    private val selectedPeople = MutableStateFlow(setOf<String>())
    private val selectedPublications = MutableStateFlow(setOf<String>())
}