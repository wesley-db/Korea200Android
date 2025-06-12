package com.korea200.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.korea200.R
import com.korea200.data.NetworkCallState
import com.korea200.ui.components.LargeTitle
import com.korea200.ui.theme.SkyBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navToHome: () -> Unit,
    work: suspend () -> NetworkCallState<Boolean>
) {
    var serverState by remember { mutableStateOf<NetworkCallState<Boolean>>( NetworkCallState.Uninitialized ) }

    //Warm up the server
    LaunchedEffect(Unit) { serverState = work() }

    //Exiting the splash screen
    LaunchedEffect(serverState) {
        //A timeout error is expected, because the server is likely to be cold at first.
        if (serverState is NetworkCallState.Success || serverState is NetworkCallState.Error) {
            delay(2000) //Ensure that splash screen is shown even when the server is warm
            navToHome()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = SkyBlue),
        contentAlignment = Alignment.BottomCenter
        ) {
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "splashBackground",
            contentScale = ContentScale.Crop
        )
        /*The 2 composable MUST come after the image to prevent being covered*/
        LargeTitle()
        CircularProgressIndicator(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPrev() {
    SplashScreen(navToHome = {}, work = suspend {NetworkCallState.Uninitialized})
}