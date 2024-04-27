package com.bayutb.generative_ai.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb.generative_ai.domain.usecase.GenerativeAIUseCase
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
    private val _result: MutableStateFlow<String> = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()
    suspend fun generate() {
        _result.value = "Generating..."
        viewModelScope.launch {
            try {
                val response = generativeAIUseCase.generate()
                if (response.candidates.isNotEmpty()) {
                    _result.value = response.candidates[0].content.parts[0].text
                } else {
                    _result.value = "No content"
                }
            } catch (e: Exception) {
                _result.value = e.message ?: "Error"
            }
        }
    }
}