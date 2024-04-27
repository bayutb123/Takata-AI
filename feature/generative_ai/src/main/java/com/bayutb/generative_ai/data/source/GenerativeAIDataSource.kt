package com.bayutb.generative_ai.data.source

import android.util.Log
import com.bayutb.generative_ai.data.request.Content
import com.bayutb.generative_ai.data.request.GenerateWithTextRequestBody
import com.bayutb.generative_ai.data.request.Part
import com.bayutb.generative_ai.data.response.GenerateWithTextResponse
import com.bayutb.generative_ai.data.service.GenerativeAIService
import com.bayutb.generative_ai.domain.repository.GenerativeAIRepository
import com.bayutb.network.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import javax.inject.Inject

class GenerativeAIDataSource @Inject constructor(
    private val generativeAIService: GenerativeAIService
) : GenerativeAIRepository {

    override suspend fun generate(prompt: String): GenerateWithTextResponse {
        return try {
            generativeAIService.generate(
                apiKey = BuildConfig.GEMINI_API_KEY,
                body = GenerateWithTextRequestBody(
                    contents = listOf(
                        Content(
                            parts = listOf(
                                Part(
                                    text = prompt
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

    override suspend fun generateWithLibrary(chatHistory: List<com.google.ai.client.generativeai.type.Content>, prompt: String): String {
        Log.d("GenerativeAIDataSource", "generateWithLibrary: $chatHistory")
        val model = GenerativeModel(
            "gemini-1.0-pro",
            BuildConfig.GEMINI_API_KEY,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 0
                topP = 0.95f
                maxOutputTokens = 8192
            },
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
            ),
        )
        val chat = model.startChat(chatHistory)
        val response = chat.sendMessage(prompt)
        return response.text ?: ""
    }
}