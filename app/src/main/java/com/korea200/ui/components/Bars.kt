package com.korea200.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    showAll: Boolean = false,
    arrowIconAction: () -> Unit = {},
    searchBarAction: (String) -> Unit,
    heartIconAction: () -> Unit
) {
    Column (
        modifier = Modifier
            .background(Color(232, 233, 235, 110))
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 14.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (showAll) {
                IconButton( onClick = arrowIconAction ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "LeftArrowIconButton",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            SearchBar(modifier = Modifier.weight(1f).padding(5.dp,0.dp), viewResult = searchBarAction)

            IconButton( onClick = heartIconAction ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "LikeIconButton",
                    tint = Color.Red,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(10.dp, 0.dp))
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun PreviewHeaders() {
    Scaffold (
        topBar = { TopBar(showAll = true, searchBarAction = {}, heartIconAction = {}) },
        content = {},
        containerColor = Color(241,234,223),
    )
}