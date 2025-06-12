package com.korea200.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kword(
    val id: String,
    @SerialName("kWord") val word: String,
    @SerialName("meaning") val dfn: List<String>? = null,
    @SerialName("audio") val audioLink: String? = null,
    @SerialName("examples") val egList: List<String>? = null
)