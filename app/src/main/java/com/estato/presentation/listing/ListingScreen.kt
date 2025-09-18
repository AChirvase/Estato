package com.estato.presentation.listing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.estato.presentation.listing.components.ListingScaffoldContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    onNavigateToDetails: (String) -> Unit,
    viewModel: ListingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ListingIntent.LoadRealEstates)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Real Estate") },
                actions = {
                    IconButton(
                        onClick = { viewModel.handleIntent(ListingIntent.RefreshRealEstates) }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        ListingScaffoldContent(
            state = state,
            onNavigateToDetails = onNavigateToDetails,
            paddingValues = paddingValues
        )
    }
}

