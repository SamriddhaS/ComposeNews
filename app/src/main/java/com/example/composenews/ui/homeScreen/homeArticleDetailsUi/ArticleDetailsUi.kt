package com.example.composenews.ui.homeScreen.homeArticleDetailsUi

import android.content.res.Configuration
import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.Typography
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composenews.R
import com.example.composenews.data.BlockingFakePostsRepository
import com.example.composenews.data.StaticPostRepository
import com.example.composenews.data.post3
import com.example.composenews.models.Markup
import com.example.composenews.models.MarkupType
import com.example.composenews.models.Paragraph
import com.example.composenews.models.ParagraphType
import com.example.composenews.models.Post
import com.example.composenews.ui.theme.ComposeNewsTheme
import kotlinx.coroutines.runBlocking


private val defaultSpacerSize = 16.dp

/**
 * Article Details Screen is shown once user has clicked on some article.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsUi(
    post: Post,
    isExpandedScreen: Boolean,
    onBack: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        topBar = {
            DetailsScreenTopAppBar(
                post.publication?.name.orEmpty(),
                navigationIconContent = {
                    if (!isExpandedScreen) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            if (!isExpandedScreen) {
                BottomAppBar(
                    actions = {
                        FavoriteButton(onClick = { })
                        BookmarkButton(isBookmarked = isFavorite, onClick = onToggleFavorite)
                        ShareButton(onClick = { })
                        TextSettingsButton(onClick = { })
                    }
                )
            }
        }
    ) { innerPadding ->
        PostContent(
            post = post,
            contentPadding = innerPadding,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            state = lazyListState
        )

    }

}

@Composable
fun PostContent(
    post: Post,
    contentPadding: PaddingValues,
    modifier: Modifier,
    state: LazyListState
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier.padding(horizontal = defaultSpacerSize),
        state = state,
    ) {
        item {

            val imageModifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 180.dp)
                .clip(shape = MaterialTheme.shapes.medium)
            Image(
                painter = painterResource(post.imageId),
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )

            Spacer(Modifier.height(defaultSpacerSize))

            Text(post.title, style = MaterialTheme.typography.headlineLarge)

            Spacer(Modifier.height(8.dp))

            if (post.subtitle != null) {
                Text(post.subtitle, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(defaultSpacerSize))
            }

            Row(
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.size(45.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(LocalContentColor.current),
                    contentScale = ContentScale.Fit
                )

                Column {
                    Text(
                        text = post.metadata.author.name,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Text(
                        text = "${post.metadata.date} • ${post.metadata.readTimeMinutes} min read",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }
        }

        items(post.paragraphs) {
            ArticleBody(it)
        }

    }
}

@Composable
fun ArticleBody(paragraph: Paragraph) {

    val (textStyle, paragraphStyle, trailingPadding) = paragraph.type.getTextAndParagraphStyle()

    val textStyleFinal = textStyle.merge(paragraphStyle)

    val annotatedString = paragraphToAnnotatedString(
        paragraph,
        MaterialTheme.typography,
        MaterialTheme.colorScheme.codeBlockBackground
    )

    Box(modifier = Modifier.padding(trailingPadding)) {
        when (paragraph.type) {
            ParagraphType.Bullet -> {
                Row {
                    with(LocalDensity.current) {
                        // this box is acting as a character, so it's sized with font scaling (sp)
                        Box(
                            modifier = Modifier
                                .size(8.sp.toDp(), 8.sp.toDp())
                                .alignBy {
                                    // Add an alignment "baseline" 1sp below the bottom of the circle
                                    9.sp.roundToPx()
                                }
                                .background(LocalContentColor.current, CircleShape),
                        )
                    }
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .alignBy(FirstBaseline),
                        text = paragraph.text,
                        style = textStyleFinal
                    )
                }
            }

            ParagraphType.CodeBlock -> {
                Surface(
                    color = MaterialTheme.colorScheme.codeBlockBackground,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = paragraph.text,
                        style = textStyleFinal
                    )
                }
            }

            ParagraphType.Header -> {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = annotatedString,
                    style = textStyleFinal
                )
            }

            else -> Text(
                modifier = Modifier.padding(4.dp),
                text = annotatedString,
                style = textStyle
            )
        }
    }
}

private data class ParagraphStyling(
    val textStyle: TextStyle,
    val paragraphStyle: ParagraphStyle,
    val trailingPadding: Dp
)

@Composable
private fun ParagraphType.getTextAndParagraphStyle(): ParagraphStyling {
    val typography = MaterialTheme.typography
    var textStyle: TextStyle = typography.bodyLarge
    var paragraphStyle = ParagraphStyle()
    var trailingPadding = 8.dp

    when (this) {
        ParagraphType.Caption -> textStyle = typography.labelMedium
        ParagraphType.Title -> textStyle = typography.headlineLarge
        ParagraphType.Subhead -> {
            textStyle = typography.headlineSmall
            trailingPadding = 4.dp
        }

        ParagraphType.Text -> {
            textStyle = typography.bodyLarge.copy(lineHeight = 28.sp)
        }

        ParagraphType.Header -> {
            textStyle = typography.headlineMedium
            trailingPadding = 4.dp
        }

        ParagraphType.CodeBlock -> textStyle = typography.bodyLarge.copy(
            fontFamily = FontFamily.Monospace
        )

        ParagraphType.Quote -> textStyle = typography.bodyLarge
        ParagraphType.Bullet -> {
            paragraphStyle = ParagraphStyle(textIndent = TextIndent(firstLine = 8.sp))
        }
    }
    return ParagraphStyling(
        textStyle,
        paragraphStyle,
        trailingPadding
    )
}

private fun paragraphToAnnotatedString(
    paragraph: Paragraph,
    typography: Typography,
    codeBlockBackground: Color
): AnnotatedString {
    val styles: List<AnnotatedString.Range<SpanStyle>> = paragraph.markups
        .map { it.toAnnotatedStringItem(typography, codeBlockBackground) }
    return AnnotatedString(text = paragraph.text, spanStyles = styles)
}

fun Markup.toAnnotatedStringItem(
    typography: Typography,
    codeBlockBackground: Color
): AnnotatedString.Range<SpanStyle> {
    return when (this.type) {
        MarkupType.Italic -> {
            AnnotatedString.Range(
                typography.bodyLarge.copy(fontStyle = FontStyle.Italic).toSpanStyle(),
                start,
                end
            )
        }

        MarkupType.Link -> {
            AnnotatedString.Range(
                typography.bodyLarge.copy(textDecoration = TextDecoration.Underline).toSpanStyle(),
                start,
                end
            )
        }

        MarkupType.Bold -> {
            AnnotatedString.Range(
                typography.bodyLarge.copy(fontWeight = FontWeight.Bold).toSpanStyle(),
                start,
                end
            )
        }

        MarkupType.Code -> {
            AnnotatedString.Range(
                typography.bodyLarge
                    .copy(
                        background = codeBlockBackground,
                        fontFamily = FontFamily.Monospace
                    ).toSpanStyle(),
                start,
                end
            )
        }
    }
}

private val ColorScheme.codeBlockBackground: Color
    get() = onSurface.copy(alpha = .15f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenTopAppBar(
    titleText: String,
    navigationIconContent: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.icon_article_background),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(36.dp)
                )
                Text(
                    text = "Published in: \n${titleText}",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        navigationIcon = navigationIconContent,
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
fun FavoriteButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.ThumbUpOffAlt,
            contentDescription = ""
        )
    }
}

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clickLabel = if (isBookmarked) "unbookmark" else "bookmark"
    IconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier.semantics {
            // Use a custom click label that accessibility services can communicate to the user.
            // We only want to override the label, not the actual action, so for the action we pass null.
            this.onClick(label = clickLabel, action = null)
        }
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            contentDescription = null // handled by click label of parent
        )
    }
}

@Composable
fun ShareButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = ""
        )
    }
}

@Composable
fun TextSettingsButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_text_settings),
            contentDescription = ""
        )
    }
}


@Preview("Article screen")
@Preview("Article screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Article screen (big font)", fontScale = 1.5f)
@Composable
fun PreviewArticleDrawer() {
    ComposeNewsTheme {
        val post = runBlocking {
            (BlockingFakePostsRepository().getPostById(post3.id) as com.example.composenews.data.Result.Success).data
        }
        ArticleDetailsUi(post, false, {}, false, {})
    }
}

@Preview("Article screen navrail", device = Devices.PIXEL_C)
@Preview(
    "Article screen navrail (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Preview("Article screen navrail (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewArticleNavRail() {
    ComposeNewsTheme {
        val post = runBlocking {
            (StaticPostRepository().getPostById(post3.id) as com.example.composenews.data.Result.Success).data
        }
        ArticleDetailsUi(post, true, {}, false, {})
    }
}
