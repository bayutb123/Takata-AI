package com.bayutb.generative_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb.generative_ai.domain.usecase.GenerativeAIUseCase
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerativeAIViewModel @Inject constructor(
    private val generativeAIUseCase: GenerativeAIUseCase
) : ViewModel() {
    private val _resultIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resultIsLoading: StateFlow<Boolean> = _resultIsLoading.asStateFlow()

    private val _chatHistory: MutableStateFlow<MutableList<Content>> = MutableStateFlow(mutableListOf())

    private val _chatHistoryForUi: MutableStateFlow<MutableList<Content>> = MutableStateFlow(mutableListOf())
    val chatHistoryForUi: StateFlow<MutableList<Content>> = _chatHistoryForUi.asStateFlow()

    private fun addChatHistory(content: Content) {
        _chatHistory.value.add(content)
    }

    suspend fun generateWithText(prompt: String) {
        _resultIsLoading.value = true
        viewModelScope.launch {
            try {
                _chatHistoryForUi.value.add(content("user") {
                    text(prompt)
                })
                val response = generativeAIUseCase.generateWithLibrary(_chatHistory.value, prompt)
                _resultIsLoading.value = false
                val newPartFromUser = content("user") {
                    text(prompt)
                }
                addChatHistory(newPartFromUser)
                val newPartFromAI = content("model") {
                    text(response)
                }
                _chatHistoryForUi.value.add(newPartFromAI)
                addChatHistory(newPartFromAI)
            } catch (e: Exception) {
                _resultIsLoading.value = false
            }
        }
    }
}