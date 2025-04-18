package com.example.livefrontdemo.view.compose.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.repository.model.AccountDetail
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme
import com.example.livefrontdemo.view.compose.reusable.AvatarImage
import com.example.livefrontdemo.view.compose.reusable.BannerImage
import com.example.livefrontdemo.view.stateholder.model.AccountState

@Composable
fun AccountRootView(
    accountState: AccountState,
    modifier: Modifier = Modifier,
) {
    when (accountState) {
        is AccountState.Success -> {
            Column(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                BannerImage(
                    imageUrl = accountState.accountDetail.bannerUrl.orEmpty(),
                    contentDescription = stringResource(
                        R.string.banner_content_description,
                        accountState.accountDetail.displayName.orEmpty()
                    ),
                    height = 136.dp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp),
                ) {
                    accountState.accountDetail.avatarUrl?.let { imageUrl ->
                        AvatarImage(
                            imageUrl = imageUrl,
                            contentDescription = stringResource(
                                id = R.string.avatar_content_description,
                                accountState.accountDetail.displayName.orEmpty()
                            ),
                            size = 72.dp,
                            modifier = Modifier.border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = accountState.accountDetail.displayName.orEmpty(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = accountState.accountDetail.handle.orEmpty(),
                            style = MaterialTheme.typography.titleSmall,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = accountState.accountDetail.description.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text(
                            text = "${accountState.accountDetail.followersCount}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.followers),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = "${accountState.accountDetail.followingCount}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.following),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        is AccountState.Error -> {
            Text(
                text = stringResource(id = accountState.message),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
            )
        }

        AccountState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        AccountState.Unknown -> Unit
    }
}

@PreviewLightDark
@Composable
private fun AccountRootViewPreview() {
    LivefrontDemoTheme {
        AccountRootView(
            accountState = AccountState.Success(
                accountDetail = AccountDetail(
                    handle = "ajkueterman.com",
                    displayName = "AJ",
                    avatarUrl = "",
                    description = "Hello World, here's my longer description with some cool stuff.\n\nhttps://ajkueterman.com",
                    followersCount = 0,
                    followingCount = 0,
                )
            )
        )
    }
}