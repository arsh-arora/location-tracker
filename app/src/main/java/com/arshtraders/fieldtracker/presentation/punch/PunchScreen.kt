package com.arshtraders.fieldtracker.presentation.punch

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arshtraders.fieldtracker.domain.punch.PunchType

@Composable
fun PunchScreen(
    viewModel: PunchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = uiState) {
            is PunchUiState.Idle -> {
                PunchIdleContent(
                    lastPunch = currentState.lastPunch,
                    onPunchClick = { viewModel.performPunch(it) }
                )
            }
            is PunchUiState.Loading -> {
                PunchLoadingContent()
            }
            is PunchUiState.Success -> {
                PunchSuccessContent(
                    result = currentState,
                    onDismiss = { viewModel.resetState() }
                )
            }
            is PunchUiState.Error -> {
                PunchErrorContent(
                    error = currentState.message,
                    onRetry = { viewModel.resetState() }
                )
            }
        }
    }
}

@Composable
fun PunchIdleContent(
    lastPunch: PunchInfo?,
    onPunchClick: (PunchType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Last punch info
        lastPunch?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Last Punch",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = it.placeName,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${it.type} at ${formatTime(it.timestamp)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
        
        // Punch button
        val punchType = if (lastPunch?.type == "MANUAL_IN") {
            PunchType.MANUAL_OUT
        } else {
            PunchType.MANUAL_IN
        }
        
        Button(
            onClick = { onPunchClick(punchType) },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (punchType == PunchType.MANUAL_IN) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (punchType == PunchType.MANUAL_IN) "📍 PUNCH IN" else "📍 PUNCH OUT",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Tap to record your location",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun PunchLoadingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    ) {
        CircularProgressIndicator()
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Getting your location...",
            style = MaterialTheme.typography.bodyLarge
        )
        
        Text(
            text = "Please wait",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun PunchSuccessContent(
    result: PunchUiState.Success,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "✅",
                style = MaterialTheme.typography.displayLarge
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Punch Successful",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = result.placeName,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Distance: ${result.serverDistance.toInt()}m from center",
                style = MaterialTheme.typography.bodySmall
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
fun PunchErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "❌",
                style = MaterialTheme.typography.displayLarge
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Punch Failed",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Try Again")
            }
        }
    }
}

fun formatTime(timestamp: Long): String {
    val formatter = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
    return formatter.format(java.util.Date(timestamp))
}