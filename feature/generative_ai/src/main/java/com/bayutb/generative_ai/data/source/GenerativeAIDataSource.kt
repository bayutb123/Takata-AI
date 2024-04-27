package com.bayutb.generative_ai.data.source

import com.bayutb.generative_ai.data.request.Content
import com.bayutb.generative_ai.data.request.GenerateWithTextRequestBody
import com.bayutb.generative_ai.data.request.Part
import com.bayutb.generative_ai.data.response.GenerateWithTextResponse
import com.bayutb.generative_ai.data.service.GenerativeAIService
import com.bayutb.generative_ai.domain.repository.GenerativeAIRepository
import com.bayutb.network.BuildConfig
import javax.inject.Inject

class GenerativeAIDataSource @Inject constructor(
    private val generativeAIService: GenerativeAIService
) : GenerativeAIRepository {
    override suspend fun generate(): GenerateWithTextResponse {
        return try {
            generativeAIService.generate(
                apiKey = BuildConfig.GEMINI_API_KEY,
                body = GenerateWithTextRequestBody(
                    contents = listOf(
                        Content(
                            parts = listOf(
                                Part(
                                    text = "Hi, I am Bayu. I am a software engineer. Write a story about me."
                                )
                            )
                        )
                    )
                )
            )
        } catch (e: Exception) {
            throw e
        }
    }
}