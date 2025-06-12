package com.korea200.data

import com.korea200.utils.GetRespSerializer
import kotlinx.serialization.Serializable

@Serializable
data class TranslateResp (
    val message: GetResp<Kword>
)

@Serializable
data class SavingWordResp (
    val message: String
)

@Serializable
data class SearchUserDataResp(
    val message: GetResp<DatabaseDoc>
)

@Serializable(with = GetRespSerializer::class)
sealed interface GetResp<out T>{
    @Serializable data class ListMssg<T>(val value: List<T>): GetResp<T>
    @Serializable data class StrMssg(val value: String): GetResp<Nothing>
}
