package com.bayutb.generative_ai.data.service

import com.bayutb.generative_ai.data.request.GenerateWithTextRequestBody
import com.bayutb.generative_ai.data.response.GenerateWithTextResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GenerativeAIService {
    @POST("v1beta/models/gemini-pro:generateContent")
    suspend fun generate(
        @Query("key") apiKey: String,
        @Body body: GenerateWithTextRequestBody
    ): GenerateWithTextResponse
}