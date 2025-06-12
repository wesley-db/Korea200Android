package com.korea200.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseDoc(
    val id: String,
    @SerialName("kWord") val kword: String,
    val meaning:List<String>,
    val name: String
)
