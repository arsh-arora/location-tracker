package com.arshtraders.fieldtracker.data.database.dao

import androidx.room.*
import com.arshtraders.fieldtracker.data.database.entities.TeamEntity
import com.arshtraders.fieldtracker.data.database.entities.TeamMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: TeamEntity)
    
    @Update
    suspend fun updateTeam(team: TeamEntity)
    
    @Query("SELECT * FROM teams WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveTeams(): Flow<List<TeamEntity>>
    
    @Query("SELECT * FROM teams WHERE id = :teamId")
    suspend fun getTeamById(teamId: String): TeamEntity?
    
    @Query("SELECT * FROM teams WHERE managerId = :managerId AND isActive = 1")
    fun getTeamsByManager(managerId: String): Flow<List<TeamEntity>>
    
    @Query("UPDATE teams SET isActive = :isActive WHERE id = :teamId")
    suspend fun updateTeamActiveStatus(teamId: String, isActive: Boolean)
    
    // Team Members
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamMember(teamMember: TeamMemberEntity)
    
    @Query("DELETE FROM team_members WHERE teamId = :teamId AND userId = :userId")
    suspend fun removeTeamMember(teamId: String, userId: String)
    
    @Query("""
        SELECT tm.*, u.name as userName, u.email as userEmail, u.role as userRole
        FROM team_members tm 
        INNER JOIN users u ON tm.userId = u.id 
        WHERE tm.teamId = :teamId AND tm.isActive = 1
        ORDER BY tm.role DESC, u.name ASC
    """)
    fun getTeamMembers(teamId: String): Flow<List<TeamMemberWithUser>>
    
    @Query("""
        SELECT t.*, tm.role as memberRole
        FROM teams t 
        INNER JOIN team_members tm ON t.id = tm.teamId 
        WHERE tm.userId = :userId AND t.isActive = 1 AND tm.isActive = 1
        ORDER BY t.name ASC
    """)
    fun getUserTeams(userId: String): Flow<List<TeamWithMemberRole>>
    
    @Query("SELECT COUNT(*) FROM team_members WHERE teamId = :teamId AND isActive = 1")
    suspend fun getTeamMemberCount(teamId: String): Int
    
    @Query("""
        UPDATE team_members 
        SET role = :role 
        WHERE teamId = :teamId AND userId = :userId
    """)
    suspend fun updateMemberRole(teamId: String, userId: String, role: String)
}

data class TeamMemberWithUser(
    val teamId: String,
    val userId: String,
    val role: String,
    val joinedAt: Long,
    val isActive: Boolean,
    val userName: String,
    val userEmail: String,
    val userRole: String
)

data class TeamWithMemberRole(
    val id: String,
    val name: String,
    val description: String?,
    val managerId: String,
    val createdBy: String,
    val isActive: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val memberRole: String
)