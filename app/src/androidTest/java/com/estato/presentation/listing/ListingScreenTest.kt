package com.estato.presentation.listing

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.estato.ui.theme.EstatoTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listingScreen_displaysTitle() {
        composeTestRule.setContent {
            EstatoTheme {
                ListingScreen(
                    onNavigateToDetails = { }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Real Estate")
            .assertIsDisplayed()
    }
}
