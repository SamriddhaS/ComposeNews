package com.example.composenews.ui.homeScreen.homeFeedUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composenews.data.posts.posts
import com.example.composenews.models.Post
import com.example.composenews.ui.theme.ComposeNewsTheme

@Composable
fun PostListHighlightedStoriesSection(highlightedPost: Post, onSelectPost: (postId: String) -> Unit) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = "Highlighted Stories For You",
        style = MaterialTheme.typography.titleLarge
    )
    TopStoriesCard(
        highlightedPost,
        Modifier.clickable { onSelectPost(highlightedPost.id) }
    )

    PostListDivider()
}

@Composable
fun TopStoriesCard(highlightedPost: Post, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val imageModifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)

        Image(
            painter = painterResource(id = highlightedPost.imageId),
            contentDescription = "",
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = highlightedPost.title,
            style = typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = highlightedPost.metadata.author.name,
            style = typography.labelLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "${highlightedPost.metadata.date} - ${highlightedPost.metadata.readTimeMinutes} min read",
            style = typography.bodySmall
        )

    }
}

@Composable
fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}

@Preview
@Composable
fun PostCardTopPreview() {
    ComposeNewsTheme {
        Surface {
            TopStoriesCard(posts.highlightedPost)
        }
    }
}