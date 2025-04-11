package com.example.livefrontdemo.view.compose.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme
import com.example.livefrontdemo.view.compose.reusable.AvatarImage

@Composable
fun FeedDetailView(
    post: TimelinePost,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        post.avatarUrl?.let { url ->
            AvatarImage(
                imageUrl = url,
                contentDescription = "${post.authorName} avatar image",
                size = 44.dp,
            )
        }

        Column(
            modifier = Modifier.padding(start = 8.dp),
        ) {
            Text(
                text = post.authorName.orEmpty(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = post.authorHandle.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun FeedDetailViewPreview() {
    LivefrontDemoTheme {
        FeedDetailView(
            post = TimelinePost(
                authorName = "John Doe",
                authorHandle = "johndoe.com",
                avatarUrl = "something",
                text = "This is a post. There can be a certain number of words in a post. Also other things, but this is the most basic."
            )
        )
    }
}