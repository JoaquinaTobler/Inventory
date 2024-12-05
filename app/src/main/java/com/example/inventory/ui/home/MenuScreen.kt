package com.example.inventory.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.R
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

object MenuDestination : NavigationDestination {
    override val route = "menu"
    override val titleRes = R.string.app_menu
}
/**
 * Entry route for Home screen
 */
@Composable
fun MenuScreen(
    navigateToUsers: () -> Unit,
    navigateToBooks: (Int) -> Unit,
    navigateToLoans: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título "MENU"
        Text(
            text = "MENU",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de "USUARIOS" y "BOOKS"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navigateToUsers() },
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .padding(8.dp)
            ) {
                Text(text = "USUARIOS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { }  ,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .padding(8.dp)
            ) {
                Text(text = "BOOKS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
        Image(painter = painterResource(id = R.drawable.logo_masculino_user),
            contentDescription = "USERS", // Poner una descripción si es necesario
            modifier = Modifier
                .size(60.dp) // Ajusta el tamaño de la imagen según tus necesidades
                .padding(end = dimensionResource(id = R.dimen.padding_small)) // Espacio entre la imagen y el texto)
        )
        // Botón para "LOANS"
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp)
        ) {
            Text(text = "LOANS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}




