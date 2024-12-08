package com.example.composenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.composenews.data.interests.StaticInterestRepository
import com.example.composenews.data.posts.StaticPostRepository
import com.example.composenews.ui.CompNewsApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val widthSizeClass = calculateWindowSizeClass(activity = this@MainActivity).widthSizeClass
            CompNewsApp(
                widthSizeClass = widthSizeClass,
                postsRepository = StaticPostRepository(),
                interestRepository = StaticInterestRepository()
            )
        }
    }
}