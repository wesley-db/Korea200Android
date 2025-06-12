package com.korea200.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korea200.data.DatabaseDoc
import com.korea200.data.GetResp
import com.korea200.data.Kword
import com.korea200.data.NetworkCallState
import com.korea200.utils.RetrofitInstance
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ServerViewModel(): ViewModel(){
    //ties to fun translate()
    var translateState by mutableStateOf<NetworkCallState<SnapshotStateList<Kword>>>(NetworkCallState.Uninitialized)
        private set
    //ties to fun savingWord()
    val insertingState = MutableSharedFlow<NetworkCallState<String>>(replay = 0, extraBufferCapacity = 0)
    //ties to fun searchUserData()
    var searchState by mutableStateOf<NetworkCallState<SnapshotStateList<DatabaseDoc>>>(NetworkCallState.Uninitialized)
        private set

    private val UNKNOWN_ERROR_MSSG = "Hmm..\n Something Went Wrong,\n Please Try Again."
    private val WORD_NOT_FOUND = "Word Not Found,\n Maybe Check Your Spelling?"
    private val USER_NOT_FOUND = { name: String -> "No data is found associated with $name"}

    fun translate(kword: String) {
        translateState = NetworkCallState.Loading

        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.server.search(kword)
                when (resp.message) {
                    is GetResp.ListMssg -> {
                        if (resp.message.value.isEmpty())
                            translateState = NetworkCallState.Error(WORD_NOT_FOUND)
                        else
                            translateState = NetworkCallState.Success(resp.message.value.toMutableStateList())
                    }
                    is GetResp.StrMssg -> {
                        Log.e("vm", "${resp.message.value}")
                        translateState = NetworkCallState.Error(UNKNOWN_ERROR_MSSG)
                    }
                }
            } catch (e: Exception) {
                Log.e("vm", "${e.message}")
                translateState = NetworkCallState.Error(mssg = UNKNOWN_ERROR_MSSG)
            }
        }
    }

    fun savingWord(
        name: String,
        kword: String,
        kwordId: String,
        dfn: List<String>
    ) {
        viewModelScope.launch {
            try {
                val data = DatabaseDoc(id = kwordId, kword = kword, meaning = dfn, name = name)
                val response = RetrofitInstance.server.savingWord(kword = data)
                insertingState.emit(NetworkCallState.Success(result = response.message))
                Log.e("vm", "${response.message}")
            } catch (e: Exception) {
                Log.e("vm", "${e.message}")
                insertingState.emit(NetworkCallState.Error(mssg = UNKNOWN_ERROR_MSSG))
            }
        }
    }

    fun searchUserData(userName: String) {
        searchState = NetworkCallState.Loading

        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.server.getUserData(name = userName)
                when (resp.message) {
                    is GetResp.ListMssg -> {
                        if (resp.message.value.isEmpty())
                            searchState = NetworkCallState.Error(USER_NOT_FOUND(userName))
                        else
                            searchState = NetworkCallState.Success(resp.message.value.toMutableStateList())
                    }
                    is GetResp.StrMssg -> {
                        Log.e("vm", "${resp.message.value}")
                        searchState = NetworkCallState.Error(UNKNOWN_ERROR_MSSG)
                    }
                }
            } catch (e: Exception) {
                Log.e("vm", "${e.message}")
                searchState = NetworkCallState.Error(mssg = UNKNOWN_ERROR_MSSG)
            }
        }
    }
}