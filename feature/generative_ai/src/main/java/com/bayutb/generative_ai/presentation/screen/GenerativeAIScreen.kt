package com.bayutb.generative_ai.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb.generative_ai.presentation.viewmodel.GenerativeAIViewModel
import com.bayutb.ui.component.animations.Loading
import com.bayutb.ui.component.input.MainChatInput
import com.google.ai.client.generativeai.type.asTextOrNull
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerativeAIScreen(
    onNavigationRequested: (String) -> Unit,
    generativeAIViewModel: GenerativeAIViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val isLoading by generativeAIViewModel.resultIsLoading.collectAsState()
    val chatState by generativeAIViewModel.chatHistoryForUi.collectAsState()
    val scrollState = rememberScrollState()
    var bottomSheetVisible by remember { mutableStateOf(false) }
    var geminiVersion by remember { mutableStateOf("gemini-1.0-pro") }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Takata AI")
                    Text(
                        text = "Dev Version",
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
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
                            Text(
                                text = "You", style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }

                        "model" -> {
                            Text(
                                text = "Takata AI", style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                    content.parts.forEach { part ->
                        MarkdownText(
                            markdown = part.asTextOrNull() ?: "", style = TextStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
                if (isLoading) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Loading(Modifier.height(56.dp))
                    }
                }
            }
            Text(
                text = "using $geminiVersion",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
            ChatInput(scope, generativeAIViewModel) { bottomSheetVisible = it }
            BottomSheet(
                bottomSheetVisible,
                scope,
                { bottomSheetVisible = false }
            ) {
                geminiVersion = it
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheet(
    bottomSheetVisible: Boolean,
    scope: CoroutineScope,
    onDismiss: () -> Unit,
    onChangeModel: (String) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    if (bottomSheetVisible) {
        ModalBottomSheet(onDismissRequest = { onDismiss() }, sheetState = bottomSheetState) {
            Column(modifier = Modifier.padding(16.dp)) {
                ListItem(
                    headlineContent = { Text("Gemini API", color = Color.Gray)},
                    supportingContent = { Text("Use gemini v1beta API", color = Color.Gray) },
                    modifier = Modifier.clickable(
                        enabled = false
                    ) {
                        scope.launch {
                            onChangeModel("v1beta")
                            delay(300)
                            bottomSheetState.hide()
                            onDismiss()
                        }
                    }
                )
                ListItem(
                    headlineContent = { Text("Gemini Model") },
                    supportingContent = { Text("Use gemini-1.0-pro Model") },
                    modifier = Modifier.clickable {
                        scope.launch {
                            onChangeModel("gemini-1.0-pro")
                            delay(300)
                            bottomSheetState.hide()
                            onDismiss()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ChatInput(
    scope: CoroutineScope,
    generativeAIViewModel: GenerativeAIViewModel,
    triggerBottomSheet: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainChatInput(
            onSendClick = { prompt ->
                scope.launch {
                    generativeAIViewModel.generateWithText(prompt)
                }
            }, modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = { triggerBottomSheet(true) },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}