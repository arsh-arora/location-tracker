package com.arshtraders.fieldtracker.presentation.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arshtraders.fieldtracker.data.network.dto.CreateTeamRequestDto
import com.arshtraders.fieldtracker.data.network.dto.TeamDto
import com.arshtraders.fieldtracker.data.network.dto.UpdateTeamRequestDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTeamDialog(
    onDismiss: () -> Unit,
    onAddTeam: (CreateTeamRequestDto) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var managerId by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Create New Team",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Team Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = managerId,
                    onValueChange = { managerId = it },
                    label = { Text("Manager ID *") },
                    placeholder = { Text("Enter manager's user ID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (name.isNotBlank() && managerId.isNotBlank()) {
                                onAddTeam(
                                    CreateTeamRequestDto(
                                        name = name.trim(),
                                        description = description.takeIf { it.isNotBlank() },
                                        managerId = managerId.trim()
                                    )
                                )
                            }
                        },
                        enabled = name.isNotBlank() && managerId.isNotBlank()
                    ) {
                        Text("Create Team")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTeamDialog(
    team: TeamDto,
    onDismiss: () -> Unit,
    onUpdateTeam: (UpdateTeamRequestDto) -> Unit
) {
    var name by remember { mutableStateOf(team.name) }
    var description by remember { mutableStateOf(team.description ?: "") }
    var managerId by remember { mutableStateOf(team.managerId) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Edit Team",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Team Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = managerId,
                    onValueChange = { managerId = it },
                    label = { Text("Manager ID *") },
                    placeholder = { Text("Enter manager's user ID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (name.isNotBlank() && managerId.isNotBlank()) {
                                onUpdateTeam(
                                    UpdateTeamRequestDto(
                                        name = name.trim(),
                                        description = description.takeIf { it.isNotBlank() },
                                        managerId = managerId.trim()
                                    )
                                )
                            }
                        },
                        enabled = name.isNotBlank() && managerId.isNotBlank()
                    ) {
                        Text("Update Team")
                    }
                }
            }
        }
    }
}