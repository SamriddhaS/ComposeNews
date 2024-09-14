package com.example.composenews.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.layout.Row
import androidx.glance.text.Text
import com.example.composenews.data.posts
import com.example.composenews.models.Post
import com.example.composenews.ui.theme.ComposeNewsTheme

@Composable
fun PostsListRecentPostUi(recentPosts: List<Post>, onSelectPost: (postId: String) -> Unit) {

    Column(modifier = Modifier.padding(16.dp)) {
        recentPosts.forEach {
            RecentPostSingleItem(
                it,
                onSelectPost
            )
        }
    }
}

@Composable
fun RecentPostSingleItem(post: Post, onSelectPost: (postId: String) -> Unit) {
    androidx.compose.foundation.layout.Row(modifier = Modifier
        .clickable { onSelectPost(post.id) }
        .padding(top = 12.dp, bottom = 12.dp)
    ) {

        var dialogState by rememberSaveable {
            mutableStateOf(false)
        }

        Image(
            painter = painterResource(post.imageThumbId),
            contentDescription = null, // decorative
            modifier = Modifier
                .size(40.dp, 40.dp)
                .clip(MaterialTheme.shapes.small)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            androidx.compose.material3.Text(
                text = "Based on your history",
                style = MaterialTheme.typography.labelMedium
            )

            androidx.compose.material3.Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            androidx.compose.material3.Text(
                text = "${post.metadata.author.name} - ${post.metadata.readTimeMinutes} min read",
                style = MaterialTheme.typography.bodyMedium
            )

        }

        IconButton(onClick = { dialogState = true }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = ""
            )
        }

        if(dialogState){
            AlertDialog(
                modifier = Modifier.padding(20.dp),
                onDismissRequest = { dialogState = false },
                title = {
                    androidx.compose.material3.Text(
                        text = "Oh Ho ho",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    androidx.compose.material3.Text(
                        text = "What the hell I am doing!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                confirmButton = {
                    androidx.compose.material3.Text(
                        text = "Close Dialog",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(15.dp)
                            .clickable { dialogState = false }
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun PostsListRecentPostUiPreview() {
    ComposeNewsTheme {
        Surface {
            PostsListRecentPostUi(
                recentPosts = posts.recentPosts,
                onSelectPost = {}
            )
        }
    }
}