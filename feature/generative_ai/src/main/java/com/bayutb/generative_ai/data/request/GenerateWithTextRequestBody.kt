package com.bayutb.generative_ai.data.request

data class GenerateWithTextRequestBody(
    val contents: List<Content>
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)