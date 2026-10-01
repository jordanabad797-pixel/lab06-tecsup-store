package com.tecsup.tecsupstore.navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun EncabezadoDrawer(nombre: String, correo: String) {
    val iniciales = nombre.split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .map { it.first() }
        .joinToString("")
        .uppercase()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iniciales,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = correo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val favoritos = remember { mutableStateListOf<Int>() }
    val alternarFavorito: (Int) -> Unit = { id ->
        if (favoritos.contains(id)) favoritos.remove(id) else favoritos.add(id)
    }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route ?: Rutas.INICIO

    val tituloPantalla = if (rutaActual == Rutas.INICIO) {
        "TECSUP Store"
    } else {
        destinosDrawer.find { it.ruta == rutaActual }?.titulo ?: "TECSUP Store"
    }

    val coloresItem = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        selectedIconColor = MaterialTheme.colorScheme.primary
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                EncabezadoDrawer(
                    nombre = "Jordan Abad",
                    correo = "jordan.abad@tecsup.edu.pe"
                )
                Spacer(modifier = Modifier.height(12.dp))
                destinosDrawer.forEach { destino ->
                    val activo = rutaActual == destino.ruta
                    val cantidad = if (destino.ruta == Rutas.FAVORITOS) favoritos.size else 0
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = destino.titulo,
                                fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        icon = { Icon(destino.icono, contentDescription = null) },
                        badge = {
                            if (cantidad > 0) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ) {
                                    Text(text = cantidad.toString())
                                }
                            }
                        },
                        selected = activo,
                        colors = coloresItem,
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
                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
                    selected = false,
                    colors = coloresItem,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(text = tituloPantalla, fontWeight = FontWeight.Bold)
                            if (rutaActual == Rutas.INICIO) {
                                Text(
                                    text = "Más vendidos",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Rutas.INICIO,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Rutas.INICIO) {
                    TiendaScreen(
                        favoritos = favoritos,
                        onToggleFavorito = alternarFavorito
                    )
                }
                composable(Rutas.PEDIDOS) { PedidosScreen() }
                composable(Rutas.FAVORITOS) {
                    FavoritosScreen(
                        favoritos = favoritos,
                        onToggleFavorito = alternarFavorito
                    )
                }
                composable(Rutas.PERFIL) { PerfilScreen() }
            }
        }
    }
}