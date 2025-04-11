package com.example.livefrontdemo.view.compose.reusable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AvatarImage(
    imageUrl: String,
    contentDescription: String,
    size: Dp = 36.dp,
    modifier: Modifier = Modifier,
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Gray.toArgb())
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
            modifier = modifier
                .size(size)
                .clip(CircleShape)
        )
    }
}

@PreviewLightDark
@Composable
private fun AvatarImagePreview() {
    LivefrontDemoTheme {
        Surface {
            AvatarImage(
                imageUrl = "",
                contentDescription = "",
                size = 100.dp,
                modifier = Modifier
                    .padding(24.dp)
            )
        }
    }
}