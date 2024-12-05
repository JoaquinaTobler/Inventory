/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.home.MenuDestination
import com.example.inventory.ui.home.MenuScreen
import com.example.inventory.ui.item.UserDetailsDestination
import com.example.inventory.ui.item.UserDetailsScreen
import com.example.inventory.ui.item.UserEditDestination
import com.example.inventory.ui.item.UserEditScreen
import com.example.inventory.ui.item.UserEntryDestination
import com.example.inventory.ui.item.UserEntryScreen
@Composable
fun InventoryNavHost(
    navController: NavHostController, //controla la navegacion
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, //define la navegacion
        startDestination = MenuDestination.route,
        //HomeDestination.route, //define la pantalla inicial
        modifier = modifier
    ) {
        composable(route = MenuDestination.route){
        MenuScreen(
            navigateToUsers= { navController.navigate(HomeDestination.route) },
            navigateToBooks = { },
            navigateToLoans = { }
            )
        }
        composable(route = HomeDestination.route) { //navegar a la entrada y usuario y la actualizacion
            HomeScreen(
                navigateToUserEntry = { navController.navigate(UserEntryDestination.route) },
                navigateToUserUpdate = {
                    navController.navigate("${UserDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = UserEntryDestination.route) { //pantalla para agregar nuevos usuarios
            UserEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        //muestra los detalles de un usuario y permite navegar hacia la pantalla de edicion
        composable(
            route = UserDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(UserDetailsDestination.userIdArg) {
                type = NavType.IntType
            })
        ) {
            UserDetailsScreen(
                navigateToEditUser = { navController.navigate("${UserEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        // patalla para editar los detalles del usuario
        composable(
            route = UserEditDestination.routeWithArgs,
            arguments = listOf(navArgument(UserEditDestination.userIdArg) {
                type = NavType.IntType
            })
        ) {
            UserEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}