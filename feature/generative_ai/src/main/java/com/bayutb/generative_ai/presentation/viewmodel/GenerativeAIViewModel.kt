package com.bayutb.generative_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb.generative_ai.domain.usecase.GenerativeAIUseCase
import com.google.ai.client.generativeai.type.Content
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
    val chatHistory: StateFlow<MutableList<Content>> = _chatHistory.asStateFlow()
    suspend fun generateWithText(prompt: String) {
        _resultIsLoading.value = true
        viewModelScope.launch {
            try {
                val response = generativeAIUseCase.generateWithLibrary(_chatHistory.value, prompt)
                _resultIsLoading.value = false
                val newPartFromUser = content("user") {
                    text(prompt)
                }
                _chatHistory.value.add(newPartFromUser)
                val newPartFromAI = content("model") {
                    text(response)
                }
                _chatHistory.value.add(newPartFromAI)
            } catch (e: Exception) {
                _resultIsLoading.value = false
            }
        }
    }
}