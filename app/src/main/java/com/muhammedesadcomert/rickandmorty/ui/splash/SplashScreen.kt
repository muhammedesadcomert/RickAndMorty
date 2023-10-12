package com.muhammedesadcomert.rickandmorty.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.muhammedesadcomert.rickandmorty.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToHome: () -> Unit) {
    val viewModel: SplashViewModel = hiltViewModel()
    val welcomeText = stringResource(R.string.welcome)
    val helloText = stringResource(R.string.hello)
    val text = remember {
        if (viewModel.isFirstOpen) {
            welcomeText
        } else {
            helloText
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        context.getDrawable(R.mipmap.ic_launcher)?.toBitmap()?.asImageBitmap()?.let {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                bitmap = it,
                contentDescription = stringResource(R.string.splash_screen)
            )
        }
        Text(text = text, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    }
    LaunchedEffect(key1 = true) {
        delay(1000)
        viewModel.isFirstOpen = false
        navigateToHome()
    }
}