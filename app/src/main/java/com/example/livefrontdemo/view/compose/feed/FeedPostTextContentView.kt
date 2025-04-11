package com.example.livefrontdemo.view.compose.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeedPostTextContentView(
    post: TimelinePost,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FlowRow {
            Text(
                text = post.authorName.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = post.authorHandle.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Start,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.text,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@PreviewFontScale
@Composable
private fun FeedPostTextContentViewPreview() {
    LivefrontDemoTheme {
        Surface {
            FeedPostTextContentView(
                post = TimelinePost(
                    authorName = "John Doe has an extra long name",
                    authorHandle = "johndoe.com",
                    avatarUrl = "something",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
                )
            )
        }
    }
}