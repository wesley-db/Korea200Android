package com.korea200.utils

import com.korea200.data.DatabaseDoc
import com.korea200.data.SavingWordResp
import com.korea200.data.SearchUserDataResp
import com.korea200.data.TranslateResp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Server {
    @GET("isWarm")
    suspend fun isWarm()
    
    @GET("translate")
    suspend fun search(
        @Query("kWord") kword: String
    ):TranslateResp

    @POST("savingWord")
    suspend fun savingWord(
        @Body kword: DatabaseDoc,
    ):SavingWordResp

    @GET("searchUserData")
    suspend fun getUserData (
        @Query("name") name: String
    ):SearchUserDataResp
}