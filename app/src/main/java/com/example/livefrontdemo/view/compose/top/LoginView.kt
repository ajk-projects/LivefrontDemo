package com.example.livefrontdemo.view.compose.top

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livefrontdemo.R
import com.example.livefrontdemo.ui.theme.LivefrontDemoTheme
import com.example.livefrontdemo.view.compose.tags.TestTags
import com.example.livefrontdemo.view.util.TwoStringParamInputHandler

@Composable
fun LoginView(
    initSession: TwoStringParamInputHandler,
    modifier: Modifier = Modifier,
    @StringRes errorMessage: Int? = null,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.log_in_to_bluesky),
            fontStyle = FontStyle.Normal,
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = username,
            label = {
                Text(text = stringResource(id = R.string.username))
            },
            onValueChange = { updatedValue ->
                username = updatedValue
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Next,
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { updatedValue ->
                password = updatedValue
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Done,
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 56.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    initSession(username, password)
                },
            ) {
                Text(
                    text = stringResource(id = R.string.log_in),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage?.let { stringResource(id = it) }.orEmpty(),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.testTag(TestTags.Login.LOGIN_ERROR),
        )
        Spacer(modifier = Modifier.imePadding())
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun LoginViewPreview() {
    LivefrontDemoTheme {
        LoginView(
            initSession = { _, _ -> },
            errorMessage = R.string.login_error
        )
    }
}