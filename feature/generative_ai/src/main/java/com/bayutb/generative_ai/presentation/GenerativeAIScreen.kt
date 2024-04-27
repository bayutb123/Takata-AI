package com.bayutb.generative_ai.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bayutb.ui.component.input.MainChatInput

@Composable
fun GenerativeAIScreen(
    onNavigationRequested: (GenerativeAIDestinations) -> Unit
) {
    Scaffold(
        topBar = {
            Text(
                text = "Generative AI",
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValue)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Generative AI")
                Button(onClick = {}, modifier = Modifier.padding(16.dp)) {
                    Text(text = "Test Button")
                }
            }
            MainChatInput(onSendClick = {}, modifier = Modifier.fillMaxWidth())
        }
    }
}