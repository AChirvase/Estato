package com.estato.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.estato.presentation.details.DetailsScreen
import com.estato.presentation.listing.ListingScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "listing"
    ) {
        composable("listing") {
            ListingScreen(
                onNavigateToDetails = { realEstateId ->
                    navController.navigate("details/$realEstateId")
                }
            )
        }

        composable("details/{realEstateId}") { backStackEntry ->
            val realEstateId = backStackEntry.arguments?.getString("realEstateId") ?: ""
            DetailsScreen(
                realEstateId = realEstateId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
