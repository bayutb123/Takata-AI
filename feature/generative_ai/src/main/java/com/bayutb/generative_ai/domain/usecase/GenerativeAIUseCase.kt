package com.bayutb.generative_ai.domain.usecase

import com.bayutb.generative_ai.data.response.GenerateWithTextResponse
import com.bayutb.generative_ai.domain.repository.GenerativeAIRepository
import javax.inject.Inject

class GenerativeAIUseCase @Inject constructor(
    private val generativeAIRepository: GenerativeAIRepository
) {
    suspend fun generateWithText(prompt: String): GenerateWithTextResponse {
        return generativeAIRepository.generate(prompt)
    }
}