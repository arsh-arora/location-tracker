package com.arshtraders.fieldtracker.presentation.tracking

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TrackingStatusCard(
            isTracking = uiState.isTracking,
            lastLocation = uiState.lastLocation
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { viewModel.toggleTracking() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.isTracking) 
                    MaterialTheme.colorScheme.error 
                else 
                    MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = if (uiState.isTracking) "Stop Tracking" else "Start Tracking"
            )
        }
    }
}

@Composable
fun TrackingStatusCard(
    isTracking: Boolean,
    lastLocation: LocationInfo?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isTracking) 
                MaterialTheme.colorScheme.primaryContainer
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tracking Status",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = if (isTracking) "🟢 Active" else "🔴 Inactive",
                style = MaterialTheme.typography.bodyLarge
            )
            
            lastLocation?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Last Location:",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${it.latitude}, ${it.longitude}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Accuracy: ±${it.accuracy}m",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}