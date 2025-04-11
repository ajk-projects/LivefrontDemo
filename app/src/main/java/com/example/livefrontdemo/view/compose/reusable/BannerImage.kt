package com.example.livefrontdemo.view.compose.reusable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.example.livefrontdemo.R
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BannerImage(
    imageUrl: String,
    contentDescription: String,
    height: Dp = 136.dp,
    modifier: Modifier = Modifier,
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Gray.toArgb())
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(height),
        )
    }
}

@PreviewLightDark
@Composable
private fun BannerImagePreview() {
    LivefrontDemoTheme {
        BannerImage(
            imageUrl = "",
            contentDescription = "",
        )
    }
}