package com.bayutb.generative_ai.domain.repository

import com.bayutb.generative_ai.data.response.GenerateWithTextResponse

interface GenerativeAIRepository {
    suspend fun generate(): GenerateWithTextResponse
}