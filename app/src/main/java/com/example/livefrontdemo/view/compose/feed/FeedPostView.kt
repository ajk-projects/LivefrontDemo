package com.example.livefrontdemo.view.compose.feed

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.repository.model.TimelinePost
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme
import com.example.livefrontdemo.view.compose.reusable.AvatarImage
import com.example.livefrontdemo.view.util.NoInputHandler

@Composable
fun FeedPostView(
    post: TimelinePost,
    postTapped: NoInputHandler,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = postTapped,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp,
                ),
        ) {
            post.avatarUrl?.let { url ->
                AvatarImage(
                    imageUrl = url,
                    contentDescription = stringResource(id = R.string.avatar_content_description, post.authorName.orEmpty()),
                )
            }

            FeedPostTextContentView(
                post = post,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FeedPostViewPreview() {
    LivefrontDemoTheme {
        FeedPostView(
            post = TimelinePost(
                authorName = "AJ Kueterman",
                authorHandle = "ajkueterman.com",
                avatarUrl = "something",
                text = "This is a post. There can be a certain number of words in a post. Also other things, but this is the most basic."
            ),
            postTapped = {},
        )
    }
}