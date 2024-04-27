package com.bayutb.generative_ai.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb.generative_ai.presentation.viewmodel.GenerativeAIViewModel
import com.bayutb.ui.component.input.MainChatInput
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerativeAIScreen(
    onNavigationRequested: (String) -> Unit,
    generativeAIViewModel: GenerativeAIViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val state by generativeAIViewModel.result.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Takata AI")
            })
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(text = state)
            }
            MainChatInput(
                onSendClick = { prompt ->
                    scope.launch {
                        generativeAIViewModel.generateWithText(prompt)
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}