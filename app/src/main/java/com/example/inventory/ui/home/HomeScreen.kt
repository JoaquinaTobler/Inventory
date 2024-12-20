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

package com.example.inventory.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.User
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
import androidx.compose.material3.*
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.graphics.Color


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToUserEntry: () -> Unit,
    navigateToUserUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    //val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Función de filtrado
    fun filterUsers(userList: List<User>, query: String): List<User> {
        return userList.filter { user ->
            user.name.contains(query, ignoreCase = true) ||
                    user.surname.contains(query, ignoreCase = true)
        }
    }

    var query by remember { mutableStateOf("") }

    val filteredUsers by remember { derivedStateOf { filterUsers(homeUiState.userList, query) } }

    Scaffold(
        // modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        modifier = modifier,
        topBar = {
            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = null
                //scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToUserEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) {innerPadding ->
        //var query by remember { mutableStateOf("") }
        //var active by remember { mutableStateOf(false) }

        Column (modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            /*
            DockedSearchBar(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .wrapContentHeight(),
                query = query,
                onQueryChange = {query = it},
                onSearch = {
                           active = false
                },
                active = active ,
                onActiveChange ={active = it},
                placeholder = { Text(text = "Search")},
                leadingIcon = { IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                }},
                trailingIcon = {
                    IconButton(onClick = {/*TODO*/ }, enabled = query.isNotEmpty()) {
                        Icon(imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                        
                    }
                }

            ) {
                
            }
            */
            // Barra de búsqueda sin comportamiento expansible
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Search") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(138, 43, 226), // Violeta cuando está activo
                    unfocusedBorderColor = Color(148, 0, 211), // Violeta más oscuro cuando no está activo
                    cursorColor = Color(138, 43, 226) // Color violeta del cursor
                )

            )
            HomeBody(
                userList = filteredUsers, // Usa la lista filtrada aquí
            onUserClick = navigateToUserUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding
            )
        }

    }

}

@Composable
private fun HomeBody(
    userList: List<User>,
    onUserClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (userList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_user_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            InventoryList(
                userList = userList,
                onUserClick = { onUserClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun InventoryList(
    userList: List<User>,
    onUserClick: (User) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier,
        // contentPadding = contentPadding
    ) {
        items(items = userList, key = { it.id }) { user ->
            InventoryUser(user = user,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onUserClick(user) })
        }
    }
}

//.clickable { onUserClick(item) })

@Composable
private fun InventoryUser(
    user: User, modifier: Modifier = Modifier
) {
    val imageResource = R.drawable.logo_masculino_user;

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null, // Poner una descripción si es necesario
                modifier = Modifier
                    .size(100.dp) // Ajusta el tamaño de la imagen según tus necesidades
                    .padding(end = dimensionResource(id = R.dimen.padding_small)) // Espacio entre la imagen y el texto
            )
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = user.surname,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = user.curse,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    InventoryTheme {
        HomeBody(listOf(
            User(1, "SSDD", "DDSS", "3A"),
            User(2, "SSEE", "HHDD", "5A"),
            User(3, "VVDD", "JJOO", "4B")
        ), onUserClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    InventoryTheme {
        HomeBody(listOf(), onUserClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    InventoryTheme {
        InventoryUser(
            User(1, "SSDD", "DDSS", "3A"),
        )
    }
}