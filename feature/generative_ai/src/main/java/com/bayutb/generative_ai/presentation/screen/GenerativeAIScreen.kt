package com.bayutb.generative_ai.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb.generative_ai.presentation.viewmodel.GenerativeAIViewModel
import com.bayutb.ui.component.input.MainChatInput
import com.google.ai.client.generativeai.type.asTextOrNull
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerativeAIScreen(
    onNavigationRequested: (String) -> Unit,
    generativeAIViewModel: GenerativeAIViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val isLoading by generativeAIViewModel.resultIsLoading.collectAsState()
    val chatState by generativeAIViewModel.chatHistory.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Takata AI")
                    Text(text = "Dev Version", modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp, color = Color.Gray)
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
                chatState.forEach { content ->
                    when (content.role) {
                        "user" -> {
                            Text(text = "You", style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            ))
                        }
                        "model" -> {
                            Text(text = "Takata AI", style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ))
                        }
                    }
                    content.parts.forEach { part ->
                        MarkdownText(markdown = part.asTextOrNull() ?: "", style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ))
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
                if (isLoading) {
                    CircularProgressIndicator()
                }
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