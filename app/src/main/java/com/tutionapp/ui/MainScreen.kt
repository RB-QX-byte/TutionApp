package com.tutionapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tutionapp.features.assignments.AssignmentsScreen
import com.tutionapp.features.classes.ClassesScreen
import com.tutionapp.features.profile.ProfileScreen
import com.tutionapp.features.settings.SettingsScreen
import com.tutionapp.features.settings.SettingsViewModel
import com.tutionapp.features.students.StudentsScreen
import com.tutionapp.features.tests.TestsScreen
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Students") }
    
    val isDarkMode by settingsViewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)
    
    val navigationItems = listOf(
        NavigationItem("Profile", Icons.Default.Person, "profile"),
        NavigationItem("Students", Icons.Default.People, "students"),
        NavigationItem("Assignments", Icons.Default.Assignment, "assignments"),
        NavigationItem("Classes", Icons.Default.Class, "classes"),
        NavigationItem("Tests", Icons.Default.Quiz, "tests"),
        NavigationItem("Settings", Icons.Default.Settings, "settings")
    )
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    // Header
                    Text(
                        text = "Tuition App",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    // Navigation Items
                    navigationItems.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.title) },
                            selected = selectedItem == item.title,
                            onClick = {
                                selectedItem = item.title
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Logout
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Logout, contentDescription = null) },
                        label = { Text("Logout") },
                        selected = false,
                        onClick = {
                            onLogout()
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedIconColor = MaterialTheme.colorScheme.error,
                            unselectedTextColor = MaterialTheme.colorScheme.error
                        )
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(selectedItem) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open drawer"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedItem) {
                    "Profile" -> ProfileScreen()
                    "Students" -> StudentsScreen()
                    "Assignments" -> AssignmentsScreen()
                    "Classes" -> ClassesScreen()
                    "Tests" -> TestsScreen()
                    "Settings" -> SettingsScreen()
                    else -> {
                        // Default to Students screen
                        StudentsScreen()
                    }
                }
            }
        }
    }
}
