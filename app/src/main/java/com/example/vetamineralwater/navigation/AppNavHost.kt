package com.example.vetamineralwater.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.ui.theme.screens.product.ViewProducts
import com.example.vetamineralwater.ui.theme.screens.dashboard.Dashboard
import com.example.vetamineralwater.ui.theme.screens.login.LoginScreen
import com.example.vetamineralwater.ui.theme.screens.messages.MessagingScreen
import com.example.vetamineralwater.ui.theme.screens.product.AddProductScreen
import com.example.vetamineralwater.ui.theme.screens.signup.SignupScreen
import com.example.vetamineralwater.ui.theme.screens.signup.SplashScreen
import com.example.vetamineralwater.ui.theme.screens.admin.AdminMessagingScreen
import com.example.vetamineralwater.models.Contact
import com.example.vetamineralwater.models.Product
import com.example.vetamineralwater.ui.theme.screens.contact.ContactScreen
import com.example.vetamineralwater.ui.theme.screens.calllog.CallLogScreen
import com.example.vetamineralwater.ui.theme.screens.product.UpdateProductScreen
import com.example.vetamineralwater.ui.theme.screens.privatechat.PrivateChatScreen
import com.example.vetamineralwater.ui.theme.screens.product.PaymentScreen
import com.example.vetamineralwater.ui.theme.screens.product.CartScreen


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(),
               startDestination: String = ROUTE_HOME) {
    val cartItems = remember { mutableStateListOf<Product>() }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(ROUTE_SPLASH) {
            SplashScreen {
                navController.navigate(ROUTE_REGISTER) {
                    popUpTo(ROUTE_SPLASH) { inclusive = true }
                }
            }
        }
        composable(ROUTE_REGISTER) { SignupScreen(navController) }
        composable(ROUTE_PAY) { PaymentScreen(navController, isAdmin = false) }
        composable(ROUTE_LOGIN) { LoginScreen(navController) }
        composable(ROUTE_HOME) { Dashboard(navController) }
        composable(ROUTE_DASHBOARD) { Dashboard(navController) }
        composable(ROUTE_ADD_PRODUCT) { AddProductScreen(navController, cartItems) }
        composable("$ROUTE_VIEW_PRODUCT/{isAdmin}") { backStackEntry ->
            val isAdmin = backStackEntry.arguments?.getString("isAdmin")?.toBoolean() ?: false
            ViewProducts(navController, isAdmin = isAdmin)
        }
        composable("$ROUTE_UPDATE_PRODUCT/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                UpdateProductScreen(navController, id)
            } else {
                println("Error: ID is null")
            }
        }
        composable(ROUTE_CONTACTS) {
            ContactScreen(navController, contacts = listOf(
                Contact("Calvin", "calvin1@example.com", "+254 799739529"),
                Contact("Reynolds", "reynolds2@example.com", "+254 725825449")
            )) { contact ->
                navController.navigate("$ROUTE_MESSAGES/${contact.name}")
            }
        }
        composable("$ROUTE_MESSAGES/{contactName}") { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: "Unknown"
            MessagingScreen(navController, contactName)
        }
        composable(ROUTE_ADMIN_MESSAGES) {
            AdminMessagingScreen(navController, clients = listOf(
                Contact("Klein", "klein@example.com", "+254 123453625"),
                Contact("Charles", "charles@example.com", "+254 721824335")
            ))
        }
        composable(ROUTE_CALL_LOG) {
            CallLogScreen(navController, contacts = listOf(
                Contact("Baunavista", "client1@example.com", "+254 799739529"),
                Contact("Lucas", "client2@example.com", "+254 788234659")
            ))
        }
        composable(ROUTE_PRIVATE_CHAT) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId") ?: return@composable
            val client = Contact(clientId, "$clientId@example.com", "+254 789657435")
            PrivateChatScreen(navController, client)
        }
        composable(ROUTE_CART) { CartScreen(navController, cartItems) }
        composable("$ROUTE_PAYHERE/{isAdmin}") { backStackEntry ->
            val isAdmin = backStackEntry.arguments?.getString("isAdmin")?.toBoolean() ?: false
            PaymentScreen(navController, isAdmin = isAdmin)
        }
    }
}
