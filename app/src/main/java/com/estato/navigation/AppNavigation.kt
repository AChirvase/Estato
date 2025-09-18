package com.estato.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.estato.presentation.details.DetailsScreen
import com.estato.presentation.listing.ListingScreen

object Routes {
    const val LISTING = "listing"
    const val DETAILS = "details/{realEstateId}"

    fun details(realEstateId: String) = "details/$realEstateId"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LISTING
    ) {
        composable(Routes.LISTING) {
            ListingScreen(
                onNavigateToDetails = { realEstateId ->
                    navController.navigate(Routes.details(realEstateId))
                }
            )
        }

        composable(Routes.DETAILS) { backStackEntry ->
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