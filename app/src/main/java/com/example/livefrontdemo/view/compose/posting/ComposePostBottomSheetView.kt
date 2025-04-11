package com.example.livefrontdemo.view.compose.posting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.livefrontdemo.R
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme
import com.example.livefrontdemo.view.compose.tags.TestTags
import com.example.livefrontdemo.view.stateholder.model.PostState
import com.example.livefrontdemo.view.util.NoInputHandler
import com.example.livefrontdemo.view.util.OneStringParamInputHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposePostBottomSheetView(
    postState: PostState,
    dismissBottomSheet: NoInputHandler,
    submitPost: OneStringParamInputHandler,
) {
    val maxCharacters = 300
    var postText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.new_post),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        TextField(
            value = postText,
            onValueChange = { updatedValue -> postText = updatedValue },
            minLines = 4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag(TestTags.Posting.POST_TEXT_FIELD)
        )
        Row {
            Text(
                text = "${maxCharacters - postText.count()}",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                color = if (postText.count() < (maxCharacters - 50)) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.error
                },
                modifier = Modifier.testTag(TestTags.Posting.POST_CHAR_COUNT)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (postState is PostState.Error)
                Text(
                    text = stringResource(id = postState.message),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag(TestTags.Posting.POST_ERROR)
                )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = dismissBottomSheet,
                shape = ButtonDefaults.outlinedShape,
                colors = ButtonDefaults.outlinedButtonColors(),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true),
            ) {
                Text(text = stringResource(id = R.string.discard))
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(
                onClick = {
                    if (postText.count() <= maxCharacters) {
                        submitPost(postText)
                    }
                },
                enabled = postText.count() <= maxCharacters && postText.isNotEmpty() && (postState is PostState.Posting).not(),
                modifier = Modifier.testTag(TestTags.Posting.POST_BUTTON)
            ) {
                Text(text = stringResource(id = R.string.post))
            }
        }
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun ComposablePostBottomSheetViewPreview() {
    LivefrontDemoTheme {
        ComposePostBottomSheetView(
            dismissBottomSheet = {},
            submitPost = { _ -> },
            postState = PostState.Error(message = R.string.posting_error),
        )
    }
}