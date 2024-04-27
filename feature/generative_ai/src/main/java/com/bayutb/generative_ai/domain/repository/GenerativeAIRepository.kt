package com.bayutb.generative_ai.domain.repository

import com.bayutb.generative_ai.data.response.GenerateWithTextResponse
import com.google.ai.client.generativeai.type.Content

interface GenerativeAIRepository {
    suspend fun generate(prompt: String): GenerateWithTextResponse
    suspend fun generateWithLibrary(chatHistory: List<Content>, prompt: String): String
}