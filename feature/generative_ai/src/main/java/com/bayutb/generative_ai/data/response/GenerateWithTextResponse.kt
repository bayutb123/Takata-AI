package com.bayutb.generative_ai.data.response

import com.google.gson.annotations.SerializedName

data class GenerateWithTextResponse(

	@field:SerializedName("candidates")
	val candidates: List<CandidatesItem>
)

data class SafetyRatingsItem(

	@field:SerializedName("probability")
	val probability: String,

	@field:SerializedName("category")
	val category: String
)

data class Content(

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("parts")
	val parts: List<PartsItem>
)

data class CandidatesItem(

	@field:SerializedName("finishReason")
	val finishReason: String,

	@field:SerializedName("index")
	val index: Int,

	@field:SerializedName("safetyRatings")
	val safetyRatings: List<SafetyRatingsItem>,

	@field:SerializedName("content")
	val content: Content
)

data class PartsItem(

	@field:SerializedName("text")
	val text: String
)
