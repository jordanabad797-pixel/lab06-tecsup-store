package com.tecsup.tecsupstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.tecsupstore.components.ProductoCard
import com.tecsup.tecsupstore.model.categorias
import com.tecsup.tecsupstore.model.productosEjemplo
import kotlinx.coroutines.launch

data class DestinoDrawer(val titulo: String, val icono: ImageVector)

val destinosDrawer = listOf(
    DestinoDrawer("Inicio", Icons.Default.Home),
    DestinoDrawer("Mis pedidos", Icons.Default.ShoppingBag),
    DestinoDrawer("Favoritos", Icons.Default.Favorite),
    DestinoDrawer("Perfil", Icons.Default.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiendaScreen() {
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val productosFiltrados = if (categoriaSeleccionada == "Todos") {
        productosEjemplo
    } else {
        productosEjemplo.filter { it.categoria == categoriaSeleccionada }
    }
    val secciones = productosFiltrados.groupBy { it.categoria }

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
                        onClick = { scope.launch { drawerState.close() } },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TECSUP Store") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categorias) { categoria ->
                        FilterChip(
                            selected = categoria == categoriaSeleccionada,
                            onClick = { categoriaSeleccionada = categoria },
                            label = { Text(categoria) }
                        )
                    }
                }
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    secciones.forEach { (categoria, lista) ->
                        item {
                            Text(
                                text = categoria,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        items(lista, key = { it.id }) { producto ->
                            ProductoCard(producto)
                        }
                    }
                }
            }
        }
    }
}