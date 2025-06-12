package com.korea200.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korea200.data.DatabaseDoc
import com.korea200.data.NetworkCallState
import com.korea200.ui.components.LargeText
import com.korea200.ui.components.SimplifiedCard
import com.korea200.ui.components.TopBar
import com.korea200.ui.components.UserInputDialog
import com.korea200.viewModels.ServerViewModel

@Composable
fun RecordScreen(
    userName: String,
    vm: ServerViewModel,
    navToMain: () -> Unit,
    navToPrev: () -> Unit
) {
    val DIALOG_TITLE = "What is your name?"
    val DIALOG_PLACEHOLDER = "your name?"
    /****************************************************/
    var showDialog by rememberSaveable { mutableStateOf<Boolean>(false) }
    var currName by rememberSaveable { mutableStateOf<String>(userName) }

    /*Displaying dialog to get user's name*/
    if (showDialog) {
        UserInputDialog(
            title = DIALOG_TITLE,
            placeholderText = DIALOG_PLACEHOLDER,
            onDismiss = { showDialog = false },
            onSubmit = { name ->
                vm.searchUserData(userName = name)
                currName = name
                showDialog = false
            }
        )
    }

    ScreenBase(
        header = {
            TopBar(
                showAll = true,
                arrowIconAction = navToPrev,
                searchBarAction = { kword ->
                    vm.translate(kword)
                    navToMain() },
                heartIconAction = { showDialog = true }
            ) }
    ) { modifier ->
        val currentState = vm.searchState//this val creates a snapshot of the mutableStateOf data allowing smart casting.
        when (currentState) {
            is NetworkCallState.Uninitialized -> {/*Do nothing (something is wrong, if it reaches here)*/ }
            is NetworkCallState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }
            is NetworkCallState.Error ->
                LargeText(
                    text = currentState.mssg,
                    modifier = Modifier.fillMaxSize().padding(6.dp)
                )
            is NetworkCallState.Success -> {
                Column (
                    modifier = modifier.fillMaxSize()
                ){
                    LargeText(
                        text = "$currName's",
                        modifier = Modifier.padding(0.dp, 16.dp).fillMaxWidth(),
                        fontSize = 30.sp
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(6.dp, 1.dp, 6.dp, 4.dp)
                            .fillMaxSize()
                    ) {
                        val kwords = currentState.result
                        kwords.forEach {
                            item {
                                SimplifiedCard(
                                    kword = it.kword,
                                    translateWord = { word ->
                                        vm.translate(word)
                                        navToMain() },
                                    meaningList = it.meaning,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        /***************End of when statement************************************/
    }
}

@Preview
@Composable
fun PreviewRecordScreen() {
    val vm = ServerViewModel()
    val data1 = DatabaseDoc(id = "0000", kword = "음악", meaning = listOf("music", "melody"), name = "sam")
    val dataList = mutableStateListOf<DatabaseDoc>(data1, data1, data1, data1, data1)

    //vm.searchState = NetworkCallState.Success<SnapshotStateList<DatabaseDoc>>(dataList)
    RecordScreen(userName = "Wesley", vm = vm, navToMain = {}, navToPrev = {})
}