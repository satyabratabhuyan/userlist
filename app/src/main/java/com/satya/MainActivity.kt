package com.satya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satya.ui.screens.UserListScreen
import com.satya.ui.screens.UserDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "userList") {
                    composable("userList") {
                        UserListScreen(navController = navController)
                    }
                    composable("userDetail/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        UserDetailScreen(userId = userId)
                    }
                }
            }
        }
    }
}
