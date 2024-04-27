package com.bayutb.ui.component.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainChatInput(
    modifier: Modifier = Modifier,
    onSendClick: (String) -> Unit,
    label: String = "Type a prompt",
    placeholder: String = "Create me a story...",
) {
    var message by remember { mutableStateOf("") }
    TextField(
        value = message,
        onValueChange = { message = it },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        trailingIcon = {
            // Send button
            IconButton(onClick = {
                onSendClick(message)
                message = ""
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
            }
        },
        modifier = modifier
    )
}

// Preview
@Composable
@Preview
fun PreviewMainChatInput() {
    MainChatInput(onSendClick = {})
}