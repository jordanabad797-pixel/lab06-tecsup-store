package com.tecsup.tecsupstore.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tecsup.tecsupstore.screens.FavoritosScreen
import com.tecsup.tecsupstore.screens.PedidosScreen
import com.tecsup.tecsupstore.screens.PerfilScreen
import com.tecsup.tecsupstore.screens.TiendaScreen
import kotlinx.coroutines.launch

data class DestinoDrawer(val titulo: String, val ruta: String, val icono: ImageVector)

val destinosDrawer = listOf(
    DestinoDrawer("Inicio", Rutas.INICIO, Icons.Default.Home),
    DestinoDrawer("Mis pedidos", Rutas.PEDIDOS, Icons.Default.ShoppingBag),
    DestinoDrawer("Favoritos", Rutas.FAVORITOS, Icons.Default.Favorite),
    DestinoDrawer("Perfil", Rutas.PERFIL, Icons.Default.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route ?: Rutas.INICIO

    val tituloPantalla = if (rutaActual == Rutas.INICIO) {
        "TECSUP Store"
    } else {
        destinosDrawer.find { it.ruta == rutaActual }?.titulo ?: "TECSUP Store"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "TECSUP Store",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                destinosDrawer.forEach { destino ->
                    NavigationDrawerItem(
                        label = { Text(destino.titulo) },
                        icon = { Icon(destino.icono, contentDescription = null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(destino.ruta) {
                                popUpTo(Rutas.INICIO)
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(tituloPantalla) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Rutas.INICIO,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Rutas.INICIO) { TiendaScreen() }
                composable(Rutas.PEDIDOS) { PedidosScreen() }
                composable(Rutas.FAVORITOS) { FavoritosScreen() }
                composable(Rutas.PERFIL) { PerfilScreen() }
            }
        }
    }
}