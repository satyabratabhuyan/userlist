package com.satya.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.ui.viewmodel.UserViewModel

@Composable
fun UserListScreen(navController: NavController, viewModel: UserViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Text("Error: ${state.error}")
        }
        else -> {
            LazyColumn {
                items(state.users) { user ->
                    Text(
                        text = user.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("userDetail/${user.id}") }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
