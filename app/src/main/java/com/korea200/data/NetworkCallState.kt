package com.korea200.data

import androidx.compose.runtime.snapshots.SnapshotStateList


sealed class NetworkCallState<out T> {
    object Uninitialized: NetworkCallState<Nothing>()
    object Loading: NetworkCallState<Nothing>()
    data class Error(val mssg: String) : NetworkCallState<Nothing>()
    data class Success<T>(val result: T): NetworkCallState<T>()
}

