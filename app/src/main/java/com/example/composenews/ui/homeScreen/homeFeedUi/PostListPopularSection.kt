package com.example.composenews.ui.homeScreen.homeFeedUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composenews.data.posts
import com.example.composenews.models.Post
import com.example.composenews.ui.theme.ComposeNewsTheme


@Composable
fun PostListPopularSection(popularPosts: List<Post>, onSelectPost: (postId: String) -> Unit) {
    Column {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            text = "Popular Stories",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
                .padding(horizontal = 8.dp)
        ) {
            popularPosts.forEach {
                PopularSectionSingleItem(
                    it,
                    onSelectPost
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        PostListDivider()
    }
}

@Composable
fun PopularSectionSingleItem(post: Post, onSelectPost: (postId: String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 300.dp, height = 200.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onSelectPost(post.id)
            }
    ) {

        Box{

            Image(
                painter = painterResource(post.imageThumbId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            // 2nd Layer: Semi-transparent background over the image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(Color.Black.copy(alpha = 0.5f)) // Adjust alpha for transparency
                    .align(Alignment.BottomStart)
            )

            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .align(Alignment.BottomStart),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = post.metadata.author.name,
                    maxLines = 1,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${post.metadata.author.name} - ${post.metadata.readTimeMinutes} min read",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                    )
            }

        }

    }
}


@Preview
@Composable
fun PostListPopularSectionPreview() {
    ComposeNewsTheme {
        Surface {
            PostListPopularSection(
                popularPosts = posts.popularPosts,
                onSelectPost = {}
            )
        }
    }
}