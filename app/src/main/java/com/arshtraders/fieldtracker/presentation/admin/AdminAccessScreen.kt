package com.arshtraders.fieldtracker.presentation.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arshtraders.fieldtracker.domain.auth.RoleManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAccessScreen(
    onAdminAccessGranted: () -> Unit,
    roleManager: RoleManager
) {
    val hasAccess = roleManager.hasPermission(RoleManager.PERM_VIEW_DASHBOARD)
    val userRole = roleManager.getCurrentUserRole()
    
    LaunchedEffect(hasAccess) {
        if (hasAccess) {
            onAdminAccessGranted()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = if (hasAccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (hasAccess) "Access Granted" else "Access Denied",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = if (hasAccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (hasAccess) {
            Text(
                text = "Welcome to the Admin Dashboard",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Loading dashboard...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            CircularProgressIndicator()
        } else {
            Text(
                text = "You don't have permission to access the admin dashboard.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Current role: ${roleManager.getRoleDisplayName(userRole ?: "Unknown")}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Required permissions:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "• Team Lead role or higher",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        text = "• Dashboard view permissions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Contact your administrator to request access.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AdminRouteWrapper(
    roleManager: RoleManager,
    content: @Composable () -> Unit
) {
    var accessChecked by remember { mutableStateOf(false) }
    var hasAccess by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        hasAccess = roleManager.hasPermission(RoleManager.PERM_VIEW_DASHBOARD)
        accessChecked = true
    }
    
    if (!accessChecked) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (hasAccess) {
        content()
    } else {
        AdminAccessScreen(
            onAdminAccessGranted = { /* Already handled in LaunchedEffect */ },
            roleManager = roleManager
        )
    }
}