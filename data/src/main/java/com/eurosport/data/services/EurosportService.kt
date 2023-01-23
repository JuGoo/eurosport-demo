package com.eurosport.data.services

import com.eurosport.data.model.ArticlesResponse
import retrofit2.http.GET

internal interface EurosportService {

    @GET("/api/json-storage/bin/edfefba")
    suspend fun fetchArticles(): ArticlesResponse
}