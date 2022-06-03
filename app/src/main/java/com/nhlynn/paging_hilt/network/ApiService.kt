package com.nhlynn.paging_hilt.network

import com.nhlynn.paging_hilt.utils.MyConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(MyConstants.CHARACTER)
    suspend fun getCharacter(
        @Query("page") page: Int
    ): Response<CharacterResponse>
}