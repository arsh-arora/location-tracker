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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arshtraders.fieldtracker.data.network.dto.UserManagementDto
import com.arshtraders.fieldtracker.domain.auth.RoleManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagementScreen(
    onNavigateBack: () -> Unit,
    viewModel: UserManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showAddUserDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        viewModel.loadUsers()
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "User Management",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            
            Row {
                IconButton(onClick = { showAddUserDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add User")
                }
                IconButton(onClick = { viewModel.loadUsers() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.searchUsers(it)
            },
            label = { Text("Search users...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        val currentState = uiState
        when (currentState) {
            is UserManagementUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is UserManagementUiState.Error -> {
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
            
            is UserManagementUiState.Success -> {
                if (currentState.users.isEmpty()) {
                    Card {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = if (searchQuery.isNotEmpty()) "No users found" else "No users available",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(currentState.users) { user ->
                            UserCard(
                                user = user,
                                onEditUser = { viewModel.selectUserForEdit(user) },
                                onDeleteUser = { viewModel.deleteUser(user.id) },
                                onToggleActiveStatus = { viewModel.toggleUserActiveStatus(user.id, !user.isActive) }
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Add User Dialog
    if (showAddUserDialog) {
        AddUserDialog(
            onDismiss = { showAddUserDialog = false },
            onAddUser = { userRequest ->
                viewModel.addUser(userRequest)
                showAddUserDialog = false
            }
        )
    }
    
    // Edit User Dialog
    val currentStateForDialog = uiState
    if (currentStateForDialog is UserManagementUiState.Success) {
        currentStateForDialog.selectedUser?.let { selectedUser ->
            EditUserDialog(
                user = selectedUser,
                onDismiss = { viewModel.clearSelectedUser() },
                onUpdateUser = { userRequest ->
                    viewModel.updateUser(selectedUser.id, userRequest)
                    viewModel.clearSelectedUser()
                }
            )
        }
    }
}

@Composable
fun UserCard(
    user: UserManagementDto,
    onEditUser: () -> Unit,
    onDeleteUser: () -> Unit,
    onToggleActiveStatus: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (user.isActive) 
                MaterialTheme.colorScheme.surface 
            else 
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Role Badge
                Surface(
                    color = Color(android.graphics.Color.parseColor(getRoleColor(user.role))),
                    contentColor = Color.White,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = getRoleDisplayName(user.role),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                user.employeeId?.let { empId ->
                    Text(
                        text = "ID: $empId",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                
                user.department?.let { dept ->
                    Text(
                        text = "Dept: $dept",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                
                // Status indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (user.isActive) Icons.Default.CheckCircle else Icons.Default.Block,
                        contentDescription = null,
                        tint = if (user.isActive) Color.Green else Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (user.isActive) "Active" else "Inactive",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (user.isActive) Color.Green else Color.Red
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onToggleActiveStatus) {
                    Text(if (user.isActive) "Deactivate" else "Activate")
                }
                
                TextButton(onClick = onEditUser) {
                    Text("Edit")
                }
                
                TextButton(
                    onClick = onDeleteUser,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

private fun getRoleColor(role: String): String {
    return when (role) {
        RoleManager.ROLE_SUPER_ADMIN -> "#FF0000"
        RoleManager.ROLE_ADMIN -> "#FF6600"
        RoleManager.ROLE_MANAGER -> "#0066FF"
        RoleManager.ROLE_TEAM_LEAD -> "#00AA00"
        RoleManager.ROLE_USER -> "#666666"
        else -> "#666666"
    }
}

private fun getRoleDisplayName(role: String): String {
    return when (role) {
        RoleManager.ROLE_USER -> "User"
        RoleManager.ROLE_TEAM_LEAD -> "Team Lead"
        RoleManager.ROLE_MANAGER -> "Manager"
        RoleManager.ROLE_ADMIN -> "Admin"
        RoleManager.ROLE_SUPER_ADMIN -> "Super Admin"
        else -> role
    }
}