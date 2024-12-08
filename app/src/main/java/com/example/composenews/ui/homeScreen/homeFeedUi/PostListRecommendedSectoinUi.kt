package com.example.composenews.ui.homeScreen.homeFeedUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composenews.data.posts.posts
import com.example.composenews.models.Post
import com.example.composenews.ui.theme.ComposeNewsTheme


@Composable
fun PostListRecommendedSection(
    recommendedPostsList: List<Post>,
    onSelectPost: (postId: String) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit
) {
    Column {
        recommendedPostsList.forEach{post ->
            RecommendedPostListItem(
                post = post,
                navigateToArticle = onSelectPost,
                isFavorite = favorites.contains(post.id),
                onToggleFavorite = { onToggleFavorite(post.id) }
            )
        }
    }
}

@Composable
fun RecommendedPostListItem(
    post: Post,
    navigateToArticle: (String) -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {

    Row(
        modifier= Modifier.clickable {
            navigateToArticle(post.id)
        }
    ) {

        Image(
            painter = painterResource(post.imageThumbId),
            contentDescription = null, // decorative
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp, 40.dp)
                .clip(MaterialTheme.shapes.small)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text ="${post.metadata.author.name} - ${post.metadata.readTimeMinutes} min read",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

        }

        Icon(
            imageVector = if (isFavorite) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            contentDescription = null, // handled by click label of parent
            modifier = Modifier.padding(16.dp)
                .clickable {
                    onToggleFavorite()
                }
        )
    }

    PostListDivider()
}


@Preview
@Composable
fun PostListRecommendedSectionPreview() {
    ComposeNewsTheme {
        Surface {
            PostListRecommendedSection(
                recommendedPostsList = posts.recommendedPosts,
                onSelectPost = {},
                favorites = emptySet(),
                onToggleFavorite = {}
            )
        }
    }
}
