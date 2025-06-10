package com.tutionapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object Profile : Screen("profile")
    object Students : Screen("students")
    object Assignments : Screen("assignments")
    object Classes : Screen("classes")
    object Tests : Screen("tests")
    object Settings : Screen("settings")
}
