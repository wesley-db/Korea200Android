package com.korea200.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.korea200.data.Kword
import com.korea200.data.NetworkCallState
import com.korea200.ui.components.LargeText
import com.korea200.ui.components.LargeTitle
import com.korea200.ui.components.TopBar
import com.korea200.ui.components.UserInputDialog
import com.korea200.viewModels.ServerViewModel
import com.korea200.ui.components.KwordCard as KwordCard1

/*
 *This is the screen that the user see right after the splash screen.
 */
@Composable
fun MainScreen(
    vm: ServerViewModel,
    navToRecord: (String) -> Unit
) {
    val DIALOG_TITLE = "What is your name?"
    val DIALOG_PLACEHOLDER = "your name?"
    /****************************************************/
    var showDialog by rememberSaveable { mutableStateOf<Boolean>(false) }
    var likeWord by remember { mutableStateOf<Kword?>(null) }
    val scope = rememberCoroutineScope()
    /****************************************************/
    val context = LocalContext.current

    /*Displaying dialog to get user's name*/
    if (showDialog) {
        UserInputDialog(
            title = DIALOG_TITLE,
            placeholderText = DIALOG_PLACEHOLDER,
            onDismiss = { showDialog = false },
            onSubmit = { name ->
                likeWord?.let {
                    //if likeWord != null, then  the user wants to saved a word
                    vm.savingWord(
                        name = name,
                        kword = it.word,
                        kwordId = it.id,
                        dfn = it.dfn ?: listOf()
                    )
                    likeWord = null
                } ?: run {
                    //else, user wants to view the saved words.
                    navToRecord(name)
                    vm.searchUserData(userName = name)
                }
                showDialog = false
            }
        )
    }

    /*Displaying feedback on the insertion of a favourite word*/
    LaunchedEffect(Unit) {
        vm.insertingState.collect { result ->
            when(result) {
                is NetworkCallState.Success -> {
                    val mssg = result.result
                    Toast.makeText(context, mssg, Toast.LENGTH_SHORT).show()
                }
                is NetworkCallState.Error -> {
                    Toast.makeText(context, result.mssg, Toast.LENGTH_SHORT).show()
                }
                else -> {/*Do Nothing*/}
            }
        }
    }

    /*Declaring what the screen looks like*/
    ScreenBase(
        header = {
            TopBar(
                searchBarAction = { kword -> vm.translate(kword) },
                heartIconAction = { showDialog = true }
            )
        }
    ) { modifier ->
        val currentState = vm.translateState//this val creates a snapshot of the mutableStateOf data allowing smart casting.
        when (currentState) {
            is NetworkCallState.Uninitialized ->
                LargeTitle()
            is NetworkCallState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }
            is NetworkCallState.Error ->
                LargeText(
                    text = currentState.mssg,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                )
            is NetworkCallState.Success -> {
                val kwords = currentState.result
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(6.dp, 20.dp, 6.dp, 4.dp)
                ) {
                    kwords.forEach {
                        item {
                            KwordCard1 (
                                kword = it.word,
                                audioLink = it.audioLink,
                                meaningList = it.dfn,
                                egList = it.egList,
                                onAdd = {
                                    showDialog = true
                                    likeWord = it
                                }
                            )
                            Spacer(modifier = Modifier.height(15.dp))
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
fun PreviewMainScreen() {
    /*val vm = TranslateViewModel()
    val mongoVm = ServerViewModel()
    MainScreen(translateVm = vm, mongoVm, {})*/
}