package com.arshtraders.fieldtracker.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshtraders.fieldtracker.data.network.dto.CreateTeamRequestDto
import com.arshtraders.fieldtracker.data.network.dto.TeamDto
import com.arshtraders.fieldtracker.data.network.dto.UpdateTeamRequestDto
import com.arshtraders.fieldtracker.domain.admin.AdminRepository
import com.arshtraders.fieldtracker.domain.admin.AdminResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TeamManagementViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TeamManagementUiState>(TeamManagementUiState.Loading)
    val uiState: StateFlow<TeamManagementUiState> = _uiState.asStateFlow()
    
    private var allTeams = listOf<TeamDto>()
    private var currentSearchQuery = ""

    fun loadTeams() {
        viewModelScope.launch {
            try {
                _uiState.value = TeamManagementUiState.Loading
                
                when (val result = adminRepository.getAllTeams()) {
                    is AdminResult.Success -> {
                        allTeams = result.data
                        applyFilter()
                    }
                    is AdminResult.Error -> {
                        _uiState.value = TeamManagementUiState.Error(result.message)
                        Timber.e("Failed to load teams: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to load teams: ${e.message}"
                _uiState.value = TeamManagementUiState.Error(errorMessage)
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun searchTeams(query: String) {
        currentSearchQuery = query
        applyFilter()
    }
    
    private fun applyFilter() {
        val filteredTeams = if (currentSearchQuery.isBlank()) {
            allTeams
        } else {
            allTeams.filter { team ->
                team.name.contains(currentSearchQuery, ignoreCase = true) ||
                team.description?.contains(currentSearchQuery, ignoreCase = true) == true ||
                team.managerName?.contains(currentSearchQuery, ignoreCase = true) == true
            }
        }
        
        _uiState.value = TeamManagementUiState.Success(
            teams = filteredTeams,
            selectedTeam = (_uiState.value as? TeamManagementUiState.Success)?.selectedTeam
        )
    }
    
    fun addTeam(teamRequest: CreateTeamRequestDto) {
        viewModelScope.launch {
            try {
                when (val result = adminRepository.createTeam(teamRequest)) {
                    is AdminResult.Success -> {
                        loadTeams() // Reload to get updated list
                        Timber.d("Team created successfully")
                    }
                    is AdminResult.Error -> {
                        val currentState = _uiState.value
                        if (currentState is TeamManagementUiState.Success) {
                            _uiState.value = currentState.copy(errorMessage = result.message)
                        } else {
                            _uiState.value = TeamManagementUiState.Error(result.message)
                        }
                        Timber.e("Failed to create team: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to create team: ${e.message}"
                val currentState = _uiState.value
                if (currentState is TeamManagementUiState.Success) {
                    _uiState.value = currentState.copy(errorMessage = errorMessage)
                } else {
                    _uiState.value = TeamManagementUiState.Error(errorMessage)
                }
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun updateTeam(teamId: String, teamRequest: UpdateTeamRequestDto) {
        viewModelScope.launch {
            try {
                when (val result = adminRepository.updateTeam(teamId, teamRequest)) {
                    is AdminResult.Success -> {
                        loadTeams() // Reload to get updated list
                        Timber.d("Team updated successfully")
                    }
                    is AdminResult.Error -> {
                        val currentState = _uiState.value
                        if (currentState is TeamManagementUiState.Success) {
                            _uiState.value = currentState.copy(errorMessage = result.message)
                        } else {
                            _uiState.value = TeamManagementUiState.Error(result.message)
                        }
                        Timber.e("Failed to update team: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to update team: ${e.message}"
                val currentState = _uiState.value
                if (currentState is TeamManagementUiState.Success) {
                    _uiState.value = currentState.copy(errorMessage = errorMessage)
                } else {
                    _uiState.value = TeamManagementUiState.Error(errorMessage)
                }
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun deleteTeam(teamId: String) {
        viewModelScope.launch {
            try {
                when (val result = adminRepository.deleteTeam(teamId)) {
                    is AdminResult.Success -> {
                        loadTeams() // Reload to get updated list
                        Timber.d("Team deleted successfully")
                    }
                    is AdminResult.Error -> {
                        val currentState = _uiState.value
                        if (currentState is TeamManagementUiState.Success) {
                            _uiState.value = currentState.copy(errorMessage = result.message)
                        } else {
                            _uiState.value = TeamManagementUiState.Error(result.message)
                        }
                        Timber.e("Failed to delete team: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to delete team: ${e.message}"
                val currentState = _uiState.value
                if (currentState is TeamManagementUiState.Success) {
                    _uiState.value = currentState.copy(errorMessage = errorMessage)
                } else {
                    _uiState.value = TeamManagementUiState.Error(errorMessage)
                }
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun selectTeamForEdit(team: TeamDto) {
        val currentState = _uiState.value
        if (currentState is TeamManagementUiState.Success) {
            _uiState.value = currentState.copy(selectedTeam = team)
        }
    }
    
    fun clearSelectedTeam() {
        val currentState = _uiState.value
        if (currentState is TeamManagementUiState.Success) {
            _uiState.value = currentState.copy(selectedTeam = null)
        }
    }
}

sealed class TeamManagementUiState {
    object Loading : TeamManagementUiState()
    data class Error(val message: String) : TeamManagementUiState()
    data class Success(
        val teams: List<TeamDto>,
        val selectedTeam: TeamDto? = null,
        val errorMessage: String? = null
    ) : TeamManagementUiState()
}