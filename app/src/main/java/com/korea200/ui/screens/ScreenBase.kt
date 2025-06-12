package com.korea200.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.korea200.R
import com.korea200.ui.components.LargeTitle
import com.korea200.ui.components.TopBar


@Composable
fun ScreenBase (
    header: @Composable () -> Unit,
    body: @Composable (Modifier) -> Unit
){
    val context = LocalContext.current
    val pattern = remember(R.drawable.pattern) {
        ImageBitmap.imageResource(context.resources, R.drawable.pattern)
    }
    val patternBrush = remember(pattern) {
        ShaderBrush(
            ImageShader(
                image = pattern,
                tileModeX = TileMode.Repeated,
                tileModeY = TileMode.Repeated
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = patternBrush, alpha = 0.5F),
    ) {
        Scaffold (
            topBar = header,
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            content = {paddingValues ->
                body(Modifier.padding(paddingValues))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenBase() {
        ScreenBase(
            header = @Composable {TopBar(searchBarAction = {}, heartIconAction = {})},
            body = @Composable { LargeTitle() }
        )
}