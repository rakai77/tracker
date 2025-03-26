package com.example.tracker.presentation.navigation

sealed class Routes(val route: String) {
    data object History : Routes("history_screen")
    data object Main : Routes("main_screen")
}