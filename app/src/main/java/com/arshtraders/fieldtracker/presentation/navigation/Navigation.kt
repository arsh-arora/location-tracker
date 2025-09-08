package com.arshtraders.fieldtracker.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arshtraders.fieldtracker.presentation.punch.PunchScreen
import com.arshtraders.fieldtracker.presentation.tracking.TrackingScreen
import com.arshtraders.fieldtracker.presentation.home.HomeScreen
import com.arshtraders.fieldtracker.presentation.admin.AdminAccessScreen
import com.arshtraders.fieldtracker.presentation.admin.AdminDashboardScreen
import com.arshtraders.fieldtracker.domain.auth.RoleManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val navigationState by navigationViewModel.navigationState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Field Tracker",
                        style = MaterialTheme.typography.headlineMedium
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, navigationState)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("tracking") {
                TrackingScreen()
            }
            composable("punch") {
                PunchScreen()
            }
            composable("admin_access") {
                AdminAccessScreen(
                    onAdminAccessGranted = {
                        navController.navigate("admin_dashboard")
                    },
                    roleManager = hiltViewModel()
                )
            }
            composable("admin_dashboard") {
                AdminDashboardScreen(
                    onNavigateToUsers = {
                        navController.navigate("user_management")
                    },
                    onNavigateToTeams = {
                        navController.navigate("team_management")
                    },
                    onNavigateToAnalytics = {
                        navController.navigate("analytics")
                    },
                    onNavigateToPlaces = {
                        navController.navigate("places_management")
                    }
                )
            }
            composable("user_management") {
                com.arshtraders.fieldtracker.presentation.admin.UserManagementScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("team_management") {
                com.arshtraders.fieldtracker.presentation.admin.TeamManagementScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("analytics") {
                com.arshtraders.fieldtracker.presentation.admin.AnalyticsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("places_management") {
                com.arshtraders.fieldtracker.presentation.admin.PlacesManagementScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, navigationState: NavigationState) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Tracking") },
            label = { Text("Tracking") },
            selected = currentDestination?.hierarchy?.any { it.route == "tracking" } == true,
            onClick = {
                navController.navigate("tracking") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Schedule, contentDescription = "Punch") },
            label = { Text("Punch") },
            selected = currentDestination?.hierarchy?.any { it.route == "punch" } == true,
            onClick = {
                navController.navigate("punch") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        
        if (navigationState.hasAdminAccess) {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AdminPanelSettings, contentDescription = "Admin") },
                label = { Text("Admin") },
                selected = currentDestination?.hierarchy?.any { 
                    it.route == "admin_access" || it.route == "admin_dashboard"
                } == true,
                onClick = {
                    navController.navigate("admin_access") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}