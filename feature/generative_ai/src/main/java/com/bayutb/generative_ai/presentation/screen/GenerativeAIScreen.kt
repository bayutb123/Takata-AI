package com.bayutb.generative_ai.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb.generative_ai.presentation.screen.GenerativeAIDestinations
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
                Row {
                    Text(text = "Takata AI")
                    PlainTooltipBox(tooltip = { Text(text = "Generative AI") }, containerColor = Color.Gray) {
                        Button(onClick = {
                            onNavigationRequested(GenerativeAIDestinations.HOME)
                        }) {
                            Text(text = "Home")
                        }
                    }
                }
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
            MainChatInput(onSendClick = {
                scope.launch {
                    generativeAIViewModel.generate()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))
        }
    }
}