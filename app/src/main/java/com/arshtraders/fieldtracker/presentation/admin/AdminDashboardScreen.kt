package com.arshtraders.fieldtracker.presentation.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arshtraders.fieldtracker.data.network.dto.DashboardStatsDto
import com.arshtraders.fieldtracker.domain.auth.RoleManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateToUsers: () -> Unit,
    onNavigateToTeams: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToPlaces: () -> Unit,
    viewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Admin Dashboard",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            
            IconButton(onClick = { viewModel.loadDashboardData() }) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        val currentState = uiState
        when (currentState) {
            is AdminDashboardUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is AdminDashboardUiState.Error -> {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = currentState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            is AdminDashboardUiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Stats Overview
                        StatsOverviewSection(currentState.stats)
                    }
                    
                    item {
                        // Quick Actions
                        QuickActionsSection(
                            onNavigateToUsers = onNavigateToUsers,
                            onNavigateToTeams = onNavigateToTeams,
                            onNavigateToAnalytics = onNavigateToAnalytics,
                            onNavigateToPlaces = onNavigateToPlaces
                        )
                    }
                    
                    item {
                        // Recent Activities
                        RecentActivitiesSection(currentState.stats.recentActivities)
                    }
                }
            }
        }
    }
}

@Composable
fun StatsOverviewSection(stats: DashboardStatsDto) {
    Text(
        text = "Overview",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
    
    val statItems = listOf(
        StatItem("Total Users", stats.totalUsers.toString(), Icons.Default.People, MaterialTheme.colorScheme.primary),
        StatItem("Active Today", stats.activeUsersToday.toString(), Icons.Default.Person, MaterialTheme.colorScheme.tertiary),
        StatItem("Punches Today", stats.totalPunchesToday.toString(), Icons.Default.Schedule, MaterialTheme.colorScheme.secondary),
        StatItem("Total Teams", stats.totalTeams.toString(), Icons.Default.Groups, MaterialTheme.colorScheme.primary),
        StatItem("Online Now", stats.onlineUsers.toString(), Icons.Default.Circle, MaterialTheme.colorScheme.tertiary)
    )
    
    LazyColumn {
        items(statItems) { statItem ->
            StatCard(statItem)
        }
    }
}

@Composable
fun StatCard(statItem: StatItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = statItem.icon,
                contentDescription = null,
                tint = statItem.color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = statItem.value,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = statItem.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun QuickActionsSection(
    onNavigateToUsers: () -> Unit,
    onNavigateToTeams: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToPlaces: () -> Unit
) {
    Text(
        text = "Quick Actions",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
    
    LazyColumn {
        items(
            listOf(
                QuickAction("Manage Users", "Add, edit, and manage user accounts", Icons.Default.ManageAccounts, onNavigateToUsers),
                QuickAction("Manage Teams", "Create and organize teams", Icons.Default.Groups, onNavigateToTeams),
                QuickAction("View Analytics", "Reports and insights", Icons.Default.Analytics, onNavigateToAnalytics),
                QuickAction("Manage Places", "Configure locations and geofences", Icons.Default.Place, onNavigateToPlaces)
            )
        ) { action ->
            QuickActionCard(action)
        }
    }
}

@Composable
fun QuickActionCard(action: QuickAction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = action.onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = action.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                )
                Text(
                    text = action.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RecentActivitiesSection(activities: List<com.arshtraders.fieldtracker.data.network.dto.ActivityDto>) {
    Text(
        text = "Recent Activities",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
    
    if (activities.isEmpty()) {
        Card {
            Text(
                text = "No recent activities",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        LazyColumn {
            items(activities.take(5)) { activity ->
                ActivityCard(activity)
            }
        }
    }
}

@Composable
fun ActivityCard(activity: com.arshtraders.fieldtracker.data.network.dto.ActivityDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = when (activity.action) {
                "PUNCH_IN" -> Icons.Default.Login
                "PUNCH_OUT" -> Icons.Default.Logout
                "LOGIN" -> Icons.Default.AccountCircle
                else -> Icons.Default.Info
            }
            
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${activity.userName} ${activity.action.lowercase().replace('_', ' ')}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (activity.location != null) {
                    Text(
                        text = "at ${activity.location}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Text(
                text = formatTimeAgo(activity.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60_000 -> "Just now"
        diff < 3600_000 -> "${diff / 60_000}m ago"
        diff < 86400_000 -> "${diff / 3600_000}h ago"
        else -> "${diff / 86400_000}d ago"
    }
}

data class StatItem(
    val label: String,
    val value: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: androidx.compose.ui.graphics.Color
)

data class QuickAction(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)