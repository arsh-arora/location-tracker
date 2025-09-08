package com.arshtraders.fieldtracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arshtraders.fieldtracker.presentation.permissions.PermissionManager
import com.arshtraders.fieldtracker.presentation.theme.FieldTrackerTheme
import com.arshtraders.fieldtracker.presentation.navigation.AppNavigation
import com.arshtraders.fieldtracker.presentation.auth.LoginScreen
import com.arshtraders.fieldtracker.presentation.auth.RegisterScreen
import com.arshtraders.fieldtracker.domain.auth.AuthState
import com.arshtraders.fieldtracker.domain.auth.TokenManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var tokenManager: TokenManager
    
    private lateinit var permissionManager: PermissionManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Timber.d("MainActivity created")
        
        permissionManager = PermissionManager(this)
        
        setContent {
            FieldTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(permissionManager, tokenManager)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(permissionManager: PermissionManager, tokenManager: TokenManager) {
    val authState by tokenManager.authState.collectAsState(initial = AuthState.Loading)
    val navController = rememberNavController()
    
    when (authState) {
        is AuthState.Loading -> {
            // Show loading screen while checking authentication
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        is AuthState.Unauthenticated -> {
            // Show authentication screens
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(
                        onNavigateToRegister = {
                            navController.navigate("register")
                        },
                        onLoginSuccess = {
                            // Authentication success will be handled by the TokenManager's state change
                        }
                    )
                }
                
                composable("register") {
                    RegisterScreen(
                        onNavigateToLogin = {
                            navController.popBackStack()
                        },
                        onRegisterSuccess = {
                            // Authentication success will be handled by the TokenManager's state change
                        }
                    )
                }
            }
        }
        
        is AuthState.Authenticated -> {
            // Show app with permission check
            AuthenticatedScreen(permissionManager)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticatedScreen(permissionManager: PermissionManager) {
    var hasPermissions by remember { mutableStateOf(false) }
    var permissionChecked by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        hasPermissions = permissionManager.hasLocationPermissions()
        permissionChecked = true
    }
    
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
        }
    ) { paddingValues ->
        if (!permissionChecked) {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (!hasPermissions) {
            // Permission request state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
                            text = "📍",
                            style = MaterialTheme.typography.displayLarge
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Location Permission Required",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "This app needs location access to track your field activities.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Button(
                            onClick = {
                                permissionManager.requestLocationPermissions { granted ->
                                    hasPermissions = granted
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Grant Location Permission")
                        }
                    }
                }
            }
        } else {
            // Main app with navigation
            AppNavigation()
        }
    }
}