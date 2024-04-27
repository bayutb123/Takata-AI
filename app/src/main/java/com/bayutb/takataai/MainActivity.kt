package com.bayutb.takataai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb.navigation.home.HomeNavHost
import com.bayutb.takataai.ui.theme.TakataAITheme
import com.bayutb.ui.component.input.MainChatInput

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakataAITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeNavHost(
                        onNavigationRequested = { destination ->
                            // Handle navigation
                        }
                    )
                }
            }
        }
    }
}
